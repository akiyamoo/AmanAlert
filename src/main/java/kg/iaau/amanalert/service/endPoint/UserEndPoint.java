package kg.iaau.amanalert.service.endPoint;

import kg.iaau.amanalert.exception.UserRegisterException;
import kg.iaau.amanalert.model.user.UserMobileConfirmModel;
import kg.iaau.amanalert.model.user.UserMobileSignInModel;
import kg.iaau.amanalert.model.user.UserRegisterModel;

public interface UserEndPoint {
    String signInOrRegisterMobileUser(String phoneNumber) throws UserRegisterException;

    String resendMessage(String phoneNumber) throws UserRegisterException;

    UserMobileSignInModel confirmUser(UserMobileConfirmModel model) throws UserRegisterException;
}