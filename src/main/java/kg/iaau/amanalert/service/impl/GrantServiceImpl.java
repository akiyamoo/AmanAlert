package kg.iaau.amanalert.service.impl;

import kg.iaau.amanalert.entity.User;
import kg.iaau.amanalert.enums.Role;
import kg.iaau.amanalert.security.details.UserDetailsImpl;
import kg.iaau.amanalert.service.GrantService;
import kg.iaau.amanalert.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class GrantServiceImpl implements GrantService {

    UserService userService;

    @Override
    public void hasAny(Role... roles) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(((UserDetailsImpl) authentication.getPrincipal()).getUsername()).orElse(new User());

        if (Arrays.stream(roles).noneMatch(r -> r == user.getRole())) {
            throw new BadCredentialsException("There is no corresponding access!");
        }

        if (user.getRole() == Role.MOBILE_USER && !user.getIsActive()) {
            throw new BadCredentialsException("This mobile user is not activated!");
        }
    }
}
