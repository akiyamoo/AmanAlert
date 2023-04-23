package kg.iaau.amanalert.controller;

import kg.iaau.amanalert.model.auth.LoginRequestModel;
import kg.iaau.amanalert.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestModel loginRequest) throws AuthenticationException {
        return ResponseEntity.ok(authService.authorize(loginRequest));
    }
}