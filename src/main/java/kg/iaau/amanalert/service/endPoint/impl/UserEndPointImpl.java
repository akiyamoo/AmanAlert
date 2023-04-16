package kg.iaau.amanalert.service.endPoint.impl;

import kg.iaau.amanalert.entity.User;
import kg.iaau.amanalert.enums.Role;
import kg.iaau.amanalert.exception.UserRegisterException;
import kg.iaau.amanalert.model.user.UserMobileConfirmModel;
import kg.iaau.amanalert.model.user.UserMobileSignInModel;
import kg.iaau.amanalert.service.AuthService;
import kg.iaau.amanalert.service.SmsSenderService;
import kg.iaau.amanalert.service.UserService;
import kg.iaau.amanalert.service.endPoint.UserEndPoint;
import kg.iaau.amanalert.util.UserValidateUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserEndPointImpl implements UserEndPoint {
    UserService userService;
    SmsSenderService smsSenderService;
    AuthService authService;
    Random random = new Random();

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

        CompletableFuture.supplyAsync(
                () -> smsSenderService.sendMessage(phone, codeActivateMessage(code)),
                CompletableFuture.delayedExecutor(500L, TimeUnit.MILLISECONDS)
        );

        return "Send SMS successfully!";
    }

    @Override
    public String resendMessage(String phoneNumber) throws UserRegisterException {
        User user = userService.getUserByUsernameAndIsActive(phoneNumber, false)
                .orElseThrow(() -> new UserRegisterException("Not found mobile user was found to activate and send SMS!"));

        user.setActivateCode(getCode());
        userService.save(user);

        CompletableFuture.supplyAsync(
                () -> smsSenderService.sendMessage("+" + phoneNumber, codeActivateMessage(user.getActivateCode())),
                CompletableFuture.delayedExecutor(500L, TimeUnit.MILLISECONDS)
        );
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
