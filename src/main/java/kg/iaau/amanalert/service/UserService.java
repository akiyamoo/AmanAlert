package kg.iaau.amanalert.service;

import kg.iaau.amanalert.entity.User;
import kg.iaau.amanalert.exception.UserRegisterException;
import kg.iaau.amanalert.model.user.UserModel;
import kg.iaau.amanalert.model.user.UserRegisterModel;
import org.springframework.core.io.ByteArrayResource;

import java.util.Optional;

public interface UserService {
    boolean existsByUsername(String username);
    User save(User user);

    UserModel createUser(UserRegisterModel registerModel, ByteArrayResource imageResource) throws UserRegisterException;
    public Optional<User> getUserByUsername(String username);

    public Optional<User> getUserByUsernameAndIsActive(String username, Boolean isActive);

    String encodePassword(String password);

    byte[] getImageById(Long userId);
    UserModel editUser(User user, boolean isEditPassword);
}
