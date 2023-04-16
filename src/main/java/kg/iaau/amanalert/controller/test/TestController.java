package kg.iaau.amanalert.controller.test;

import kg.iaau.amanalert.enums.Role;
import kg.iaau.amanalert.model.auth.LoginRequestModel;
import kg.iaau.amanalert.service.GrantService;
import kg.iaau.amanalert.service.SmsSenderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TestController {

    SmsSenderService smsSenderService;
    GrantService grantService;

    @PostMapping("/send-sms")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestModel loginRequest) throws AuthenticationException {
        grantService.hasAny(Role.ADMIN);
        // TODO
        return ResponseEntity.ok(smsSenderService.sendMessage(loginRequest.getUsername(), loginRequest.getPassword()));
    }
}
