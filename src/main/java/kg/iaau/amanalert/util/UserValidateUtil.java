package kg.iaau.amanalert.util;

import kg.iaau.amanalert.exception.UserRegisterException;
import kg.iaau.amanalert.model.user.UserRegisterModel;
import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

@UtilityClass
public class UserValidateUtil {
    private static final Pattern phonePattern = Pattern.compile("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$");

    public void validatePhone(String phone) throws UserRegisterException {
        if (!phonePattern.matcher(phone).matches()) {
            throw new UserRegisterException("The phone number was entered incorrectly!");
        }
    }
}
