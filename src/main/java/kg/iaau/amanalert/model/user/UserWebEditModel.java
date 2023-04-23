package kg.iaau.amanalert.model.user;

import kg.iaau.amanalert.enums.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserWebEditModel {
    Long id;
    String username;
    String password;
    Role role;
    String phone;
    Date birthDate;
    String name;
    String email;
}
