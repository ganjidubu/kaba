package home.dubu.kaba.hyunny.search.controller;

import home.dubu.kaba.hyunny.search.domain.dto.V2Place;
import home.dubu.kaba.hyunny.search.service.V2SearchService;
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

class V2SearchControllerTest {

    V2SearchService searchService;
    MockMvc sut;

    @BeforeEach
    void setUp() {
        searchService = mock(V2SearchService.class);
        sut = MockMvcBuilders.standaloneSetup(new V2SearchController(searchService)).build();
    }

    @Test
    @DisplayName("키워드에 해당하는 모든 장소를 검색할 수 있다.")
    void getPlacesByKeyword() throws Exception {

        when(searchService.getPlacesByKeyword("짜장"))
                .thenReturn(
                        List.of(
                                V2Place.builder()
                                        .title("신의 짜장면")
                                        .address("서울시 도봉구")
                                        .build()
                        )
                );


        sut.perform(
                        get("/api/v2/places")
                                .param("keyword", "짜장")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("신의 짜장면"))
                .andExpect(jsonPath("$[0].address").value("서울시 도봉구"))
        ;
    }
}