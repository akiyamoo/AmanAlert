package kg.iaau.amanalert.service.impl;

import kg.iaau.amanalert.entity.User;
import kg.iaau.amanalert.model.auth.AuthModel;
import kg.iaau.amanalert.model.auth.LoginRequestModel;
import kg.iaau.amanalert.service.AuthService;
import kg.iaau.amanalert.service.UserService;
import kg.iaau.amanalert.util.JwtUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    JwtUtils jwtUtils;
    AuthenticationManager authenticationManager;
    UserService userService;

    @Override
    public AuthModel authorize(LoginRequestModel requestModel) throws AuthenticationException {
        try {
            log.info("username: {}", requestModel.getUsername());

            User user = userService.getUserByUsername(requestModel.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Нету такого пользователя!"));

            if (!userService.isCorrectPassword(requestModel.getPassword(), user.getPassword())) throw new UsernameNotFoundException("Wrong password!");

            String jwt = generateToken(user);

            return AuthModel.builder()
                    .username(user.getUsername())
                    .id(user.getId())
                    .message(jwt)
                    .role(user.getRole())
                    .build();
        } catch (Exception e) {
            log.info("ex: {}", e);
            throw e;
        }
    }

    @Override
    public String generateToken(User user) {
        return jwtUtils.generateTokenFromUsername(user);
    }
}
