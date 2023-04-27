package kg.iaau.amanalert.controller.mobile;

import kg.iaau.amanalert.enums.Role;
import kg.iaau.amanalert.model.user.UserMobileConfirmModel;
import kg.iaau.amanalert.model.user.UserMobileEditModel;
import kg.iaau.amanalert.service.GrantService;
import kg.iaau.amanalert.service.endPoint.UserEndPoint;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mobile/user")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserMobileController {

    UserEndPoint userEndPoint;
    GrantService grantService;

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody String phoneNumber) {
        //grantService.hasAny(Role.MOBILE_USER);
        try {
            return ResponseEntity.ok(userEndPoint.signInOrRegisterMobileUser(phoneNumber));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/sign-in/resend-sms")
    public ResponseEntity<?> resendMessage(@RequestParam String phoneNumber) {
        //grantService.hasAny(Role.MOBILE_USER);
        try {
            return ResponseEntity.ok(userEndPoint.resendMessage(phoneNumber));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/sign-in/confirm")
    public ResponseEntity<?> confirmSignIn(@RequestBody UserMobileConfirmModel model) {
        try {
            return ResponseEntity.ok(userEndPoint.confirmUser(model));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<?> editUser(@RequestBody UserMobileEditModel editModel) {
        grantService.hasAny(Role.MOBILE_USER);
        try {
            return ResponseEntity.ok(userEndPoint.editMobileUser(editModel));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/edit-avatar/{username}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> editAvatar(@PathVariable String username, @RequestParam("image") MultipartFile image) {
        grantService.hasAny(Role.MOBILE_USER);
        try {
            return ResponseEntity.ok(userEndPoint.editImageByUsername(username, image));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllMobileUsers() {
        try {
            return ResponseEntity.ok(userEndPoint.getAllMobileUsers());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
