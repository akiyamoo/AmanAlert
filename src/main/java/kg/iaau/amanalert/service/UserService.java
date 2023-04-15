package kg.iaau.amanalert.service;

import kg.iaau.amanalert.entity.User;
import kg.iaau.amanalert.exception.UserRegisterException;
import kg.iaau.amanalert.model.user.UserModel;
import kg.iaau.amanalert.model.user.UserRegisterModel;

import java.util.Optional;

public interface UserService {
    boolean existsByUsername(String username);
    User save(User user);

    UserModel registerWebUser(UserRegisterModel registerModel) throws UserRegisterException;
    public Optional<User> getUserByUsername(String username);
}
