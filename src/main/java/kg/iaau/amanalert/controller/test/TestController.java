package kg.iaau.amanalert.controller.test;

import kg.iaau.amanalert.model.auth.LoginRequestModel;
import kg.iaau.amanalert.service.SmsSenderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/send-sms")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestModel loginRequest) throws AuthenticationException {
        // TODO
        return ResponseEntity.ok(smsSenderService.sendMessage(loginRequest.getUsername(), loginRequest.getPassword()));
    }
}
