package kg.iaau.amanalert.model.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import kg.iaau.amanalert.enums.Role;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthModel {
    @Schema(description = "Сообщение об успешной авторизации (token)", example = "Вы успешно авторизовались!")
    String message;

    @Schema(description = "Идентификатор пользователя", example = "123")
    Long id;

    @Schema(description = "Имя пользователя", example = "user123")
    String username;

    @Schema(description = "Роль пользователя", example = "ADMIN")
    Role role;
}
