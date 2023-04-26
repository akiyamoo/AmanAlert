package kg.iaau.amanalert.model.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestModel {
    @NotBlank
    @Schema(description = "Имя пользователя", example = "user123")
    String username;

    @NotBlank
    @Schema(description = "Пароль пользователя", example = "password123")
    String password;
}
