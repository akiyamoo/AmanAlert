/*
 //TODO настроить
package kg.iaau.amanalert.security.config;

import kg.iaau.amanalert.entity.User;
import kg.iaau.amanalert.enums.Role;
import kg.iaau.amanalert.repo.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {

    UserRepository repository;
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        repository.save(User.builder()
                        .role(Role.ADMIN)
                        .username("admin")
                        .password(passwordEncoder.encode("Admin2023"))
                .build());
    }
}
*/
