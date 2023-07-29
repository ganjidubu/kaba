package home.dubu.kaba.najae.service;

import home.dubu.kaba.najae.entity.Search;
import home.dubu.kaba.najae.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SearchSaveService {
    private final SearchRepository repository;


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveSearchKeyword(String keyword) {
        try {
            Optional<Search> retrievedSearch = repository.findByKeyword(keyword);
            if (retrievedSearch.isPresent()) {
                retrievedSearch.get().countUp();
            } else {
                repository.save(new Search(keyword, 1));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("save 실패");
        }
    }
}
