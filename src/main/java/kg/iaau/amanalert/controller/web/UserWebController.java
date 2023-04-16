package kg.iaau.amanalert.controller.web;

import kg.iaau.amanalert.enums.Role;
import kg.iaau.amanalert.model.user.UserRegisterModel;
import kg.iaau.amanalert.service.GrantService;
import kg.iaau.amanalert.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserWebController {

    UserService userService;
    GrantService grantService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterModel registerModel) {
        grantService.hasAny(Role.WEB_USER, Role.ADMIN);

        try {
            return ResponseEntity.ok(userService.createUser(registerModel));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
