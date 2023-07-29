package home.dubu.kaba.najae.repository;

import home.dubu.kaba.najae.entity.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface SearchRepository extends JpaRepository<Search, Integer> {
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    Optional<Search> findByKeyword(String keyword);
}
