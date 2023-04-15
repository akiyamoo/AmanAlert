package kg.iaau.amanalert.repo;

import kg.iaau.amanalert.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    Optional<News> findByIdAndDeletedIsNull(Long newsId);
}
