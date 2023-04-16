package kg.iaau.amanalert.service;

import kg.iaau.amanalert.entity.User;
import kg.iaau.amanalert.model.auth.AuthModel;
import kg.iaau.amanalert.model.auth.LoginRequestModel;
import org.apache.tomcat.websocket.AuthenticationException;

public interface AuthService {
    AuthModel authorize(LoginRequestModel requestModel) throws AuthenticationException;
    String generateToken(User user);
}
