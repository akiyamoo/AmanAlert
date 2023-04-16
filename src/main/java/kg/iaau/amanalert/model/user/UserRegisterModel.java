package kg.iaau.amanalert.model.user;

import kg.iaau.amanalert.base.BaseModelFrom;
import kg.iaau.amanalert.entity.User;
import kg.iaau.amanalert.enums.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRegisterModel implements BaseModelFrom<User> {
    String username;
    String password;
    Role role;
    String phone;
    Date birthDate;
    String name;

    @Override
    public User ToEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .role(role)
                .phone(phone)
                .birthDate(birthDate)
                .name(name)
                .build();
    }
}
