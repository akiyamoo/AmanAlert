package kg.iaau.amanalert.service.impl;

import kg.iaau.amanalert.enums.Role;
import kg.iaau.amanalert.model.AuthModel;
import kg.iaau.amanalert.model.LoginRequestModel;
import kg.iaau.amanalert.security.details.UserDetailsImpl;
import kg.iaau.amanalert.service.AuthService;
import kg.iaau.amanalert.service.UserService;
import kg.iaau.amanalert.util.JwtUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    AuthenticationManager authenticationManager;

    JwtUtils jwtUtils;

    @Override
    public String getToken() {
        return "Hello";
    }

    @Override
    public AuthModel authorize(LoginRequestModel requestModel) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(requestModel.getUsername(), requestModel.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateTokenFromUsername(userDetails.getUsername());

        Role role = Role.valueOf(userDetails.getAuthorities().toArray()[0].toString());

        return AuthModel.builder()
                .username(userDetails.getUsername())
                .id(userDetails.getId())
                .message(jwt)
                .role(role)
                .build();
    }
}
