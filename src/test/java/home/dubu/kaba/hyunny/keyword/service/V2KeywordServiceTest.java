package home.dubu.kaba.hyunny.keyword.service;

import home.dubu.kaba.hyunny.keyword.domain.vo.V2HottestKeywordVO;
import home.dubu.kaba.hyunny.keyword.service.impl.V2KeywordServiceImpl;
import home.dubu.kaba.hyunny.keyword.store.V2KeywordStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class V2KeywordServiceTest {

    V2KeywordStore keywordStore;
    V2KeywordService sut;

    @BeforeEach
    void setUp() {
        keywordStore = mock(V2KeywordStore.class);
        sut = new V2KeywordServiceImpl(keywordStore);
    }

    @Test
    void getTopFiveHottestKeywords() {

        when(keywordStore.getTopFiveHottestKeywords())
                .thenReturn(
                        List.of(
                                V2HottestKeywordVO.builder()
                                        .keyword("짜장")
                                        .count(1000)
                                        .build()
                        )
                );


        var result = sut.getTopFiveHottestKeywords();


        assertEquals("짜장", result.get(0).getKeyword());
        assertEquals(1000L, result.get(0).getCount());
    }

    @Test
    void addSearchKeywordHistory() {

        sut.addSearchKeywordHistory("짜장");


        verify(keywordStore, times(1)).addSearchKeywordHistory("짜장");
    }
}