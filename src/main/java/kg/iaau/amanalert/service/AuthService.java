package kg.iaau.amanalert.service;

import kg.iaau.amanalert.model.AuthModel;
import kg.iaau.amanalert.model.LoginRequestModel;

public interface AuthService {
    String getToken();

    AuthModel authorize(LoginRequestModel requestModel);
}
