package kg.iaau.amanalert.service;

import kg.iaau.amanalert.entity.User;

public interface UserService {
    boolean existsByUsername(String username);
    User save(User user);
}
