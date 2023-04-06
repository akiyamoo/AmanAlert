package kg.iaau.amanalert.model;

import kg.iaau.amanalert.enums.Role;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthModel {
    String message;
    Long id;
    String username;
    Role role;
}
