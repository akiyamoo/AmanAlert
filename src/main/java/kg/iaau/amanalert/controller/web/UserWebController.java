package kg.iaau.amanalert.controller.web;

import kg.iaau.amanalert.enums.Role;
import kg.iaau.amanalert.exception.UserRegisterException;
import kg.iaau.amanalert.service.GrantService;
import kg.iaau.amanalert.service.endPoint.UserEndPoint;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserWebController {

    UserEndPoint userEndPoint;
    GrantService grantService;

    @GetMapping("/get-all-web")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getAllWebUsers() {
        grantService.hasAny(Role.ADMIN);
        try {
            return ResponseEntity.ok(userEndPoint.getAllWebUsers());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/get-user/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        try {
            return ResponseEntity.ok(userEndPoint.getUserByUsername(username));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public ResponseEntity<?> registerUser(@RequestParam("data") String json, @RequestParam(value = "image", required = false) MultipartFile image) {
        grantService.hasAny(Role.ADMIN);

        try {
            MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
            formData.add("data", json);
            if (image != null) {
                formData.add("image", new ByteArrayResource(image.getBytes()) {
                    @Override
                    public String getFilename() {
                        return image.getOriginalFilename();
                    }
                });
            }
            return ResponseEntity.ok(userEndPoint.createWebUser(formData));
        } catch (UserRegisterException e) {
            log.warn("registerUser(): {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("registerUser(): {}", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/edit")
    public ResponseEntity<?> editUser(@RequestParam("data") String json, @RequestParam("image") MultipartFile image) {
        grantService.hasAny(Role.ADMIN);

        try {
            MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
            formData.add("data", json);
            formData.add("image", new ByteArrayResource(image.getBytes()) {
                @Override
                public String getFilename() {
                    return image.getOriginalFilename();
                }
            });
            return ResponseEntity.ok(userEndPoint.editWebUser(formData));
        } catch (UserRegisterException e) {
            log.warn("registerUser(): {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("registerUser(): {}", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        grantService.hasAny(Role.ADMIN);
        return ResponseEntity.ok(userEndPoint.deleteUserById(id));
    }

    @GetMapping(value = "/avatar/{userId}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getAvatar(@PathVariable Long userId) {
        return userEndPoint.getImageById(userId);
    }
}