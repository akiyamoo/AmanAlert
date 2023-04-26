package kg.iaau.amanalert.repo;

import kg.iaau.amanalert.entity.Contact;
import kg.iaau.amanalert.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    Optional<Contact> findByIdAndDeletedIsNull(Long id);
    List<Contact> findAllByUserAndDeletedIsNull(User user);
}
