package home.dubu.kaba.najae.repository;

import home.dubu.kaba.najae.entity.Search;
import home.dubu.kaba.najae.repository.SearchRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SearchRepositoryTest {
    @Autowired
    private SearchRepository searchRepository;


    @DisplayName("키워드로 조회하기")
    @Test
    void findByKeyword() {
        // given
        searchRepository.save(new Search(null, "keyword", 1));

        // when
        var response = searchRepository.findByKeyword("keyword");

        // then
        assertThat(response).isNotEmpty();
        assertThat(response.get().getKeyword()).isEqualTo("keyword");
    }
}
