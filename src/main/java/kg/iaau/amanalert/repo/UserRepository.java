package kg.iaau.amanalert.repo;

import kg.iaau.amanalert.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndDeletedIsNull(String username);
    Optional<User> findByUsernameAndIsActive(String username, Boolean isActive);
}
