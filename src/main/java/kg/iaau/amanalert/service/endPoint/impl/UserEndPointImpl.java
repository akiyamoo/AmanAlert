package kg.iaau.amanalert.service.endPoint.impl;

import com.google.gson.Gson;
import kg.iaau.amanalert.entity.User;
import kg.iaau.amanalert.enums.Role;
import kg.iaau.amanalert.exception.UserRegisterException;
import kg.iaau.amanalert.model.user.*;
import kg.iaau.amanalert.security.details.UserDetailsImpl;
import kg.iaau.amanalert.service.AuthService;
import kg.iaau.amanalert.service.SmsSenderService;
import kg.iaau.amanalert.service.UserService;
import kg.iaau.amanalert.service.endPoint.UserEndPoint;
import kg.iaau.amanalert.util.UrlHostUtil;
import kg.iaau.amanalert.util.UserValidateUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserEndPointImpl implements UserEndPoint {
    UserService userService;
    SmsSenderService smsSenderService;
    AuthService authService;
    Random random = new Random();
    Gson gson = new Gson();

    @Override
    public String signInOrRegisterMobileUser(String phoneNumber) throws UserRegisterException {
        String phone = "+" + phoneNumber;
        UserValidateUtil.validatePhone(phone);

        User user = userService.getUserByUsername(phoneNumber).orElse(
                User.builder()
                        .phone(phone)
                        .role(Role.MOBILE_USER)
                        .isActive(false)
                        .username(phoneNumber)
                        .password(generatePassword())
                        .activateCode(getCode())
                        .build()
        );

        if (user.getId() != null) {
            user.setPassword(generatePassword());
            user.setActivateCode(getCode());
            user.setIsActive(false);
        }

        user = userService.save(user);
        String code = user.getActivateCode();

        try {
            CompletableFuture.supplyAsync(
                    () -> smsSenderService.sendMessage(phone, codeActivateMessage(code)),
                    CompletableFuture.delayedExecutor(500L, TimeUnit.MILLISECONDS)
            );
        } catch (Exception e) {
            log.error("{}", e);
        }

        return "Send SMS successfully!";
    }

    @Override
    public String resendMessage(String phoneNumber) throws UserRegisterException {
        User user = userService.getUserByUsernameAndIsActive(phoneNumber, false)
                .orElseThrow(() -> new UserRegisterException("Not found mobile user was found to activate and send SMS!"));

        user.setActivateCode(getCode());
        userService.save(user);

        try {
            CompletableFuture.supplyAsync(
                    () -> smsSenderService.sendMessage("+" + phoneNumber, codeActivateMessage(user.getActivateCode())),
                    CompletableFuture.delayedExecutor(500L, TimeUnit.MILLISECONDS)
            );
        } catch (Exception e) {
            log.error("{}", e);
        }
        return "Resend SMS successfully! Code: " + user.getActivateCode();
    }

    @Override
    public UserMobileSignInModel confirmUser(UserMobileConfirmModel model) throws UserRegisterException {
        User user = userService.getUserByUsernameAndIsActive(model.getPhoneNumber(), false)
                .orElseThrow(() -> new UserRegisterException("Not found mobile user was found to activate and send SMS!"));

        if (!user.getActivateCode().equals(model.getCode())) {
            throw new UserRegisterException("Invalid/Incorrect activation code!");
        }
        String password = user.getPassword();

        user.setActivateCode(null);
        user.setIsActive(true);
        user.setPassword(userService.encodePassword(password));
        user = userService.save(user);

        return UserMobileSignInModel.builder()
                .id(user.getId())
                .username(user.getUsername())
                .token(authService.generateToken(user))
                .password(password)
                .build();
    }

    @Override
    public UserModel createWebUser(MultiValueMap<String, Object> formData) throws UserRegisterException {
        UserRegisterModel model = gson.fromJson((String) formData.getFirst("data"), UserRegisterModel.class);
        ByteArrayResource imageResource = (ByteArrayResource) formData.getFirst("image");

        if (userService.getUserByUsername(model.getUsername()).isPresent()) {
            throw new UserRegisterException("Such a username already exists!");
        }
        if (Role.WEB_USER != model.getRole()) {
            throw new UserRegisterException("It is only possible to create a user with the role WEB_USER!");
        }
        if (model.getBirthDate() == null) {
            throw new UserRegisterException("The date of birth is not filled in!");
        }

        UserValidateUtil.validatePhone(model.getPhone());
        //UserValidateUtil.validateEmail(model.getEmail());
        UserValidateUtil.validatePassword(model.getPassword());
        UserValidateUtil.validateUsername(model.getUsername());

        return userService.createUser(model, imageResource);
    }

    @Override
    public byte[] getImageById(Long userId) {
        return userService.getImageById(userId);
    }

    @Override
    public UserModel editMobileUser(UserMobileEditModel editModel) throws UserRegisterException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.getUserByUsername(((UserDetailsImpl) authentication.getPrincipal()).getUsername()).orElse(new User());

        User user = userService.getUserByUsername(editModel.getUsername()).orElseThrow(
                () -> new NotFoundException("User not found!")
        );

        if (!Objects.equals(user.getId(), currentUser.getId())) {
            throw new UserRegisterException("It is impossible to edit another user!");
        }

        user.setEmail(editModel.getEmail());
        user.setName(editModel.getName());
        user.setBirthDate(editModel.getBirthDate());

        return new UserModel().toModel(userService.save(user));
    }

    @Override
    public String editImageByUsername(String username, MultipartFile image) throws IOException, UserRegisterException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.getUserByUsername(((UserDetailsImpl) authentication.getPrincipal()).getUsername()).orElse(new User());

        User user = userService.getUserByUsername(username).orElseThrow(
                () -> new NotFoundException("User not found!")
        );

        if (!Objects.equals(user.getId(), currentUser.getId())) {
            throw new UserRegisterException("It is impossible to edit another user!");
        }

        user.setImage(image.getBytes());
        user = userService.save(user);

        return UrlHostUtil.getHostUrl() + UrlHostUtil.getAvatarUrl() + user.getId();
    }

    @Override
    public UserModel editWebUser(MultiValueMap<String, Object> formData) throws UserRegisterException {
        UserWebEditModel model = gson.fromJson((String) formData.getFirst("data"), UserWebEditModel.class);
        ByteArrayResource imageResource = (ByteArrayResource) formData.getFirst("image");

        boolean isEditPassword = model.getPassword() != null;


        User user = userService.findUserById(model.getId());

        if (user == null) {
            throw new UserRegisterException("User not found!");
        }

        boolean isEditUsername = !user.getUsername().equals(model.getUsername());

        if (isEditUsername) log.info("Edit username: id: {}, username: {}", user.getId(), model.getUsername());
        if (isEditPassword) log.info("Edit password: id: {}, password: {}", user.getId(), model.getPassword());

        if (Role.WEB_USER != model.getRole()) {
            throw new UserRegisterException("It is only possible to edit a user with the role WEB_USER!");
        }
        if (model.getBirthDate() == null) {
            throw new UserRegisterException("The date of birth is not filled in!");
        }

        if (model.getUsername() == null) {
            throw new UserRegisterException("Username is empty!");
        }

        if (isEditUsername) {
            User checkUser = userService.getUserByUsername(model.getUsername()).orElse(null);

            if (checkUser != null) {
                throw new UserRegisterException("Username is exist!");
            }
        }

        UserValidateUtil.validatePhone(model.getPhone());
        //UserValidateUtil.validateEmail(model.getEmail());
        UserValidateUtil.validatePassword(model.getPassword());
        UserValidateUtil.validateUsername(model.getUsername());

        if (isEditUsername) {
            user.setUsername(model.getUsername());
        }
        user.setPhone(model.getPhone());
        user.setPassword(isEditPassword ? model.getPassword() : user.getPassword());
        user.setEmail(model.getEmail());
        user.setBirthDate(new Date(model.getBirthDate()));
        user.setImage(imageResource == null ? user.getImage() : imageResource.getByteArray());
        user.setEducation(model.getEducation());
        user.setPosition(model.getPosition());
        user.setExperience(model.getExperience());

        return userService.editUser(user, isEditPassword);
    }

    @Override
    public List<UserModel> getAllWebUsers() {
        return userService.getAllByRole(Role.WEB_USER)
                .stream()
                .map(u -> new UserModel().toModel(u))
                .collect(Collectors.toList());
    }

    @Override
    public UserModel getUserByUsername(String username) {
        return new UserModel().toModel(
                userService.getUserByUsername(username)
                        .orElseThrow(() -> new NotFoundException("User not found!"))
        );
    }

    @Override
    public List<UserModel> getAllMobileUsers() {
        return userService.getAllByRole(Role.MOBILE_USER)
                .stream()
                .map(u -> new UserModel().toModel(u))
                .collect(Collectors.toList());
    }

    @Override
    public String deleteUserById(Long id) {
        User user = userService.findUserById(id);
        user.setDeleted(new Date());
        userService.save(user);

        return "User deleted!";
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.getUserByUsername(((UserDetailsImpl) authentication.getPrincipal()).getUsername()).orElse(new User());
    }

    @Override
    public String getSmsCode(String phoneNumber) throws UserRegisterException {
        User user = userService.getUserByUsernameAndIsActive(phoneNumber, false)
                .orElseThrow(() -> new UserRegisterException("Not found mobile user was found to activate and send SMS!"));

        return user.getActivateCode();
    }

    private String codeActivateMessage(String code) {
        return String.format("Account activation code: %s", code);
    }

    private String getCode() {
        return random.nextInt(1001, 9999) + "";
    }

    private String generatePassword() {
        int length = random.nextInt(8, 15);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int flag = random.nextInt(0, 90);
            if (flag > 70) builder.append((char) (random.nextInt(26) + 'A'));
            else if (flag > 40) builder.append((char) (random.nextInt(26) + 'a'));
            else builder.append(random.nextInt(0, 9));
        }

        return builder.toString();
    }
}
