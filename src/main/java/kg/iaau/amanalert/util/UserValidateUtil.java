package kg.iaau.amanalert.util;

import kg.iaau.amanalert.exception.UserRegisterException;
import kg.iaau.amanalert.model.user.UserRegisterModel;
import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

@UtilityClass
public class UserValidateUtil {
    private static final Pattern phonePattern = Pattern.compile("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$");
    private static final Pattern emailPattern = Pattern.compile("^(.+)@(\\\\S+)$");
    private static final Pattern password = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");

    public void validatePhone(String phone) throws UserRegisterException {
        if (!phonePattern.matcher(phone).matches()) {
            throw new UserRegisterException("The phone number was entered incorrectly!");
        }
    }

    public void validateEmail(String phone) throws UserRegisterException {
        if (!phonePattern.matcher(phone).matches()) {
            throw new UserRegisterException("This email was entered incorrectly!");
        }
    }

    public void validatePassword(String password) throws UserRegisterException {
        if (password.length() < 8) {
            throw new UserRegisterException("The password must be at least 8 characters long!");
        }
        if (!password.matches(".*\\d.*")) {
            throw new UserRegisterException("The password must be at least 1 digit!");
        }
        if (!password.matches(".*[a-zA-Z].*")) {
            throw new UserRegisterException("The password must be at least 1 letter!");
        }
        if (!password.matches(".*[!@#$%^&*].*")) {
            throw new UserRegisterException("The password must be at least 1 special character (!, @, #, $)!");
        }
    }

    public void validateUsername(String username) throws UserRegisterException {
        if (username.length() < 3) {
            throw new UserRegisterException("The username must be more than 3 characters!");
        }
        if (!username.matches("^[a-zA-Z0-9._@-]+$")) {
            throw new UserRegisterException("usernames can contain only letters, numbers and some special characters, such as \"-\", \"_\", \".\", \"@\"!");
        }
    }
}
