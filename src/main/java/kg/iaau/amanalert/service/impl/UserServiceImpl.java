package kg.iaau.amanalert.service.impl;

import kg.iaau.amanalert.entity.User;
import kg.iaau.amanalert.exception.UserRegisterException;
import kg.iaau.amanalert.model.user.UserModel;
import kg.iaau.amanalert.model.user.UserRegisterModel;
import kg.iaau.amanalert.repo.UserRepository;
import kg.iaau.amanalert.service.UserService;
import kg.iaau.amanalert.util.UserValidateUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
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
    public UserModel registerWebUser(UserRegisterModel registerModel) throws UserRegisterException {
        UserValidateUtil.validateUserRegister(registerModel);

        registerModel.setPassword(encoder.encode(registerModel.getPassword()));

        return new UserModel().toModel(save(registerModel.ToEntity()));
    }

    public Optional<User> getUserByUsername(String username) {
        return repository.findByUsername(username);
    }
}