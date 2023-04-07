package kg.iaau.amanalert.model.user;

import kg.iaau.amanalert.base.BaseModelTo;
import kg.iaau.amanalert.entity.User;
import kg.iaau.amanalert.enums.Role;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModel extends BaseModelTo<User> {
    Long id;

    String username;

    Role role;

    String phone;

    Date birthDate;

    String name;

    @Override
    public UserModel toModel(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.role = user.getRole();
        this.phone = user.getPhone();
        this.birthDate = user.getBirthDate();
        this.name = user.getName();

        return this;
    }
}
