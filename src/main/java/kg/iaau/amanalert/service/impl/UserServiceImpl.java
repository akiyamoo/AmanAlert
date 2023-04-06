package kg.iaau.amanalert.service.impl;

import kg.iaau.amanalert.entity.User;
import kg.iaau.amanalert.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    // TODO
    @Override
    public boolean existsByUsername(String username) {
        return false;
    }

    @Override
    public User save(User user) {
        return null;
    }
}
