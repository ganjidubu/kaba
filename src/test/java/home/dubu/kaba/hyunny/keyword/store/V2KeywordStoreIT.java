package home.dubu.kaba.hyunny.keyword.store;

import home.dubu.kaba.hyunny.keyword.domain.entity.KeywordEntity;
import home.dubu.kaba.hyunny.keyword.store.impl.JpaV2KeywordStore;
import home.dubu.kaba.hyunny.keyword.store.repository.V2KeywordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class V2KeywordStoreIT {

    @Autowired
    EntityManager entityManager;
    @Autowired
    V2KeywordRepository keywordRepository;
    V2KeywordStore sut;

    @BeforeEach
    void setUp() {
        sut = new JpaV2KeywordStore(keywordRepository);
    }

    List<KeywordEntity> getSampleData(String keyword, int count) {
        var result = new ArrayList<KeywordEntity>();
        for (int index = 0; index < count; index++) {
            result.add(
                    KeywordEntity.builder()
                            .keyword(keyword)
                            .build()
            );
        }
        return result;
    }

    void flushAndClear(EntityManager entityManager) {
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void getTopFiveHottestKeywords() {

        var samples = getSampleData("짜장", 3);
        samples.addAll(getSampleData("짬뽕", 5));
        samples.addAll(getSampleData("똠양꿍", 4));
        samples.addAll(getSampleData("아이스크림", 1));
        samples.addAll(getSampleData("맥주", 11));
        samples.addAll(getSampleData("냉면", 2));
        for (var keywordEntity : samples) {
            entityManager.persist(keywordEntity);
        }
        flushAndClear(entityManager);


        var result = sut.getTopFiveHottestKeywords();


        assertEquals(5, result.size());
        assertEquals("맥주", result.get(0).getKeyword());
        assertEquals(11L, result.get(0).getCount());
        assertEquals("짬뽕", result.get(1).getKeyword());
        assertEquals(5L, result.get(1).getCount());
        assertEquals("똠양꿍", result.get(2).getKeyword());
        assertEquals(4L, result.get(2).getCount());
        assertEquals("짜장", result.get(3).getKeyword());
        assertEquals(3L, result.get(3).getCount());
        assertEquals("냉면", result.get(4).getKeyword());
        assertEquals(2L, result.get(4).getCount());
    }

    @Test
    void addSearchKeywordHistory() {

        sut.addSearchKeywordHistory("짜장");


        var typedQuery = entityManager.createQuery("select k from KeywordEntity k where k.keyword=:keyword", KeywordEntity.class);
        typedQuery.setParameter("keyword", "짜장");
        var result = typedQuery.getResultList();
        assertEquals("짜장", result.get(0).getKeyword());
    }
}