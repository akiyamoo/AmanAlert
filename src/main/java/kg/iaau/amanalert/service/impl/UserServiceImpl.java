package kg.iaau.amanalert.service.impl;

import kg.iaau.amanalert.entity.User;
import kg.iaau.amanalert.model.user.UserModel;
import kg.iaau.amanalert.model.user.UserRegisterModel;
import kg.iaau.amanalert.repo.UserRepository;
import kg.iaau.amanalert.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserServiceImpl implements UserService {
    UserRepository repository;
    PasswordEncoder encoder;

    @Override
    public boolean existsByUsername(String username) {
        return repository.findByUsername(username).isPresent();
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public UserModel createUser(UserRegisterModel registerModel, ByteArrayResource imageResource) {
        registerModel.setPassword(encoder.encode(registerModel.getPassword()));

        return new UserModel().toModel(save(registerModel.ToEntity().updateImage(imageResource.getByteArray())));
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return repository.findByUsernameAndDeletedIsNull(username);
    }

    @Override
    public Optional<User> getUserByUsernameAndIsActive(String username, Boolean isActive) {
        return repository.findByUsernameAndIsActive(username, isActive);
    }

    @Override
    public String encodePassword(String password) {
        return encoder.encode(password);
    }

    @Override
    public byte[] getImageById(Long userId) {
        return repository.findById(userId).orElse(new User()).getImage();
    }

    @Override
    public UserModel editUser(User user, boolean isEditPassword) {
        if (isEditPassword) user.setPassword(encodePassword(user.getPassword()));

        return new UserModel().toModel(save(user));
    }
}