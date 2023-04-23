package kg.iaau.amanalert.controller.mobile;

import kg.iaau.amanalert.model.user.UserMobileConfirmModel;
import kg.iaau.amanalert.model.user.UserMobileEditModel;
import kg.iaau.amanalert.service.GrantService;
import kg.iaau.amanalert.service.endPoint.UserEndPoint;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> resendMessage(@RequestBody String phoneNumber) {
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
        try {
            return ResponseEntity.ok(userEndPoint.editMobileUser(editModel));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/edit-avatar/{username}")
    public ResponseEntity<?> editAvatar(@PathVariable String username, @RequestParam("image") MultipartFile image) {
        try {
            return ResponseEntity.ok(userEndPoint.editImageByUsername(username, image));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
