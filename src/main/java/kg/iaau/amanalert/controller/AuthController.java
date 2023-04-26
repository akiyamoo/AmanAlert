package kg.iaau.amanalert.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.iaau.amanalert.model.auth.AuthModel;
import kg.iaau.amanalert.model.auth.LoginRequestModel;
import kg.iaau.amanalert.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Авторизация", description = "Вход и получение токена")

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    AuthService authService;

    @Operation(summary = "Авторизовать пользователя")
    @ApiResponse(responseCode = "200", description = "Успешный вход", content = {@Content(schema = @Schema(implementation = AuthModel.class))})
    @ApiResponse(responseCode = "401", description = "Fail")
    @ApiResponse(responseCode = "403", description = "Fail")
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestModel loginRequest) throws AuthenticationException {
        return ResponseEntity.ok(authService.authorize(loginRequest));
    }
}