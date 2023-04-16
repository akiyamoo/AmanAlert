package kg.iaau.amanalert.security.config;

import kg.iaau.amanalert.entity.User;
import kg.iaau.amanalert.enums.Role;
import kg.iaau.amanalert.repo.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class Runner implements CommandLineRunner {

    UserRepository repository;
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        log.info("Start Runner...");
        if (repository.findByUsername("admin").isEmpty()) {
            log.info("Create User with username = admin, password = Admin2023");
            repository.save(User.builder()
                    .role(Role.ADMIN)
                    .username("admin")
                    .password(passwordEncoder.encode("Admin2023"))
                    .build());
        }
        log.info("Finish Runner...");
    }
}
