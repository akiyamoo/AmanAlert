package kg.iaau.amanalert.repo;

import kg.iaau.amanalert.entity.Form;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FormRepository extends JpaRepository<Form, Long> {
    List<Form> findAllByDeletedIsNull();
}