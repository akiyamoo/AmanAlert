package kg.iaau.amanalert.service;

import kg.iaau.amanalert.entity.User;
import kg.iaau.amanalert.enums.Role;
import kg.iaau.amanalert.exception.UserRegisterException;
import kg.iaau.amanalert.model.user.UserModel;
import kg.iaau.amanalert.model.user.UserRegisterModel;
import org.springframework.core.io.ByteArrayResource;

import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean existsByUsername(String username);
    User save(User user);

    UserModel createUser(UserRegisterModel registerModel, ByteArrayResource imageResource) throws UserRegisterException;
    public Optional<User> getUserByUsername(String username);

    public Optional<User> getUserByUsernameAndIsActive(String username, Boolean isActive);

    String encodePassword(String password);

    boolean isCorrectPassword(String password, String encode);

    byte[] getImageById(Long userId);
    UserModel editUser(User user, boolean isEditPassword);

    List<User> getAllByRole(Role webUser);

    User findUserById(Long id);
}
