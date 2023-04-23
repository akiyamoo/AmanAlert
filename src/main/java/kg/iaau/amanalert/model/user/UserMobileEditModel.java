package kg.iaau.amanalert.model.user;

import kg.iaau.amanalert.base.BaseModelFrom;
import kg.iaau.amanalert.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserMobileEditModel {

    String username;
    Date birthDate;
    String name;
    String email;
}
