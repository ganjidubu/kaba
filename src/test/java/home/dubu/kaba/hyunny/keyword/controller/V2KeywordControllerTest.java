package home.dubu.kaba.hyunny.keyword.controller;

import home.dubu.kaba.hyunny.keyword.domain.dto.V2HottestKeyword;
import home.dubu.kaba.hyunny.keyword.service.V2KeywordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class V2KeywordControllerTest {

    V2KeywordService keywordService;
    MockMvc sut;

    @BeforeEach
    void setUp() {
        keywordService = mock(V2KeywordService.class);
        sut = MockMvcBuilders.standaloneSetup(new V2KeywordController(keywordService)).build();
    }

    @Test
    @DisplayName("가장 핫한 5개 키워드를 검색할 수 있다.")
    void getTopFiveHottestKeywords() throws Exception {

        when(keywordService.getTopFiveHottestKeywords())
                .thenReturn(
                        List.of(
                                V2HottestKeyword.builder()
                                        .keyword("짜장면")
                                        .count(5)
                                        .build()
                        )
                );


        sut.perform(
                        get("/api/v2/hottest-keywords")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].keyword").value("짜장면"))
                .andExpect(jsonPath("$[0].count").value(5))
        ;
    }
}