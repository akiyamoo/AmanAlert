package kg.iaau.amanalert.service.impl;

import kg.iaau.amanalert.enums.Role;
import kg.iaau.amanalert.model.auth.AuthModel;
import kg.iaau.amanalert.model.auth.LoginRequestModel;
import kg.iaau.amanalert.security.details.UserDetailsImpl;
import kg.iaau.amanalert.security.details.UserDetailsServiceImpl;
import kg.iaau.amanalert.service.AuthService;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    AuthenticationManager authenticationManager;
    JwtUtils jwtUtils;
    PasswordEncoder passwordEncoder;
    UserDetailsService userDetailsService;

    @Override
    public AuthModel authorize(LoginRequestModel requestModel) throws AuthenticationException {
        log.info("username: {}", requestModel.getUsername());

        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(requestModel.getUsername());

        log.info("authorize(): {}", userDetails.getUsername());
        log.info("authorize(): {}", passwordEncoder.matches(requestModel.getPassword(), userDetails.getPassword()));
        if (!passwordEncoder.matches(requestModel.getPassword(), userDetails.getPassword())) {
             throw new AuthenticationException("Не найден пользователь!");
        }

        String jwt = jwtUtils.generateTokenFromUsername(userDetails);

        Role role = Role.valueOf(userDetails.getAuthorities().toArray()[0].toString());

        return AuthModel.builder()
                .username(userDetails.getUsername())
                .id(userDetails.getId())
                .message(jwt)
                .role(role)
                .build();
    }
}
