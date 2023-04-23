package kg.iaau.amanalert.service.endPoint;

import kg.iaau.amanalert.exception.UserRegisterException;
import kg.iaau.amanalert.model.user.UserMobileConfirmModel;
import kg.iaau.amanalert.model.user.UserMobileEditModel;
import kg.iaau.amanalert.model.user.UserMobileSignInModel;
import kg.iaau.amanalert.model.user.UserModel;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserEndPoint {
    String signInOrRegisterMobileUser(String phoneNumber) throws UserRegisterException;

    String resendMessage(String phoneNumber) throws UserRegisterException;

    UserMobileSignInModel confirmUser(UserMobileConfirmModel model) throws UserRegisterException;

    UserModel createWebUser(MultiValueMap<String, Object> formData) throws UserRegisterException;

    byte[] getImageById(Long userId);

    UserModel editMobileUser(UserMobileEditModel editModel) throws UserRegisterException;

    String editImageByUsername(String username, MultipartFile image) throws IOException;
}
