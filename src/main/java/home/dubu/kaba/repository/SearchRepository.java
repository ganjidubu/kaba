package home.dubu.kaba.repository;

import home.dubu.kaba.entity.Search;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SearchRepository extends JpaRepository<Search, Integer> {
    Optional<Search> findByKeyword(String keyword);
}
