package kg.iaau.amanalert.model.user;

import kg.iaau.amanalert.base.BaseModelTo;
import kg.iaau.amanalert.entity.User;
import kg.iaau.amanalert.enums.Role;
import kg.iaau.amanalert.util.UrlHostUtil;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserModel implements BaseModelTo<User> {
    Long id;
    String username;
    Role role;
    String phone;
    String birthDate;
    String name;
    String email;
    String urlImage;

    String experience;

    String position;

    String education;

    @Override
    public UserModel toModel(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.role = user.getRole();
        this.phone = user.getPhone();
        this.birthDate = new SimpleDateFormat("yyyy-MM-dd").format(user.getBirthDate());
        this.name = user.getName();
        this.email = user.getEmail();
        this.urlImage = UrlHostUtil.getHostUrl() + UrlHostUtil.getAvatarUrl() + this.id;
        String experience = " год";
        if (user.getExperience() != null || user.getExperience() != 0) {
            if (user.getExperience() >= 2 && user.getExperience() <= 4) experience = " года";
            if (user.getExperience() > 4) experience = " лет";
            this.experience = String.format("%s" + experience, user.getExperience());
        }
        else {
            this.experience = "";
        }
        this.position = user.getPosition();
        this.education = user.getEducation();

        return this;
    }
}
