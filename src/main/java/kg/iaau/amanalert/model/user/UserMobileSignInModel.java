package kg.iaau.amanalert.model.user;

import kg.iaau.amanalert.base.BaseModelTo;
import kg.iaau.amanalert.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserMobileSignInModel implements BaseModelTo<User> {

    Long id;
    String username;
    String password;
    String token;

    @Override
    public UserMobileSignInModel toModel(User user) {
        return UserMobileSignInModel.builder()
                .id(user.getId())
                .password(user.getPassword())
                .username(user.getUsername())
                .build();
    }
}
