package kg.iaau.amanalert.service;

import kg.iaau.amanalert.model.auth.AuthModel;
import kg.iaau.amanalert.model.auth.LoginRequestModel;

public interface AuthService {
    AuthModel authorize(LoginRequestModel requestModel);
}
