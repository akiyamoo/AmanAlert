package kg.iaau.amanalert.util;

import kg.iaau.amanalert.exception.UserRegisterException;
import kg.iaau.amanalert.model.user.UserRegisterModel;
import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

@UtilityClass
public class UserValidateUtil {
    private static final Pattern pronePattern = Pattern.compile("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$");

    public void validateUserRegister(UserRegisterModel registerModel) throws UserRegisterException {
/*        if (!pronePattern.matcher(registerModel.getPhone()).matches()) {
            throw new UserRegisterException("Не корректно введён номер телефона!");
        }*/

        // TODO
    }
}
