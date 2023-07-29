package home.dubu.kaba.najae.controller;

import home.dubu.kaba.najae.dto.response.PlaceSearchResponse;
import home.dubu.kaba.najae.dto.response.SearchListResponse;
import home.dubu.kaba.najae.exception.ResultCode;
import home.dubu.kaba.najae.service.SearchService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SearchController.class)
class SearchControllerTest {

    @MockBean
    private SearchService searchService;
    @Autowired
    private MockMvc mockMvc;


    @DisplayName("장소 검색")
    @Test
    void search() throws Exception {
        // given
        when(searchService.searchByKeyword(anyString())).thenReturn(
            new PlaceSearchResponse(List.of(new PlaceSearchResponse.Result("title", "address"))));

        // when
        var response = 장소_검색_요청("곱창");

        // then
        response
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.results[0].title").value("title"))
            .andDo(print());
    }


    @DisplayName("장소 검색 - 잘못된 인자 들어왔을 때")
    @Test
    void searchWhenInvalidParameter() throws Exception {
        // when
        var response = 장소_검색_요청("");

        // then
        response
            .andExpect(status().is4xxClientError())
            .andExpect(jsonPath("$.message").value(ResultCode.INVALID_PARAMETER.getMessage()))
            .andDo(print());
    }


    @DisplayName("검색 키워드 목록")
    @Test
    void getSearchList() throws Exception {
        // given
        when(searchService.findSearchList()).thenReturn(
            new SearchListResponse(List.of(new SearchListResponse.SearchDto("keyword", 1))));

        // when
        var response = 검색_키워드_목록_요청();

        // then
        response
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.searchList[0].keyword").value("keyword"))
            .andDo(print());
    }


    private ResultActions 장소_검색_요청(String keyword) throws Exception {
        return mockMvc.perform(get("/api/v1/places/search")
                                   .param("keyword", keyword))
                      .andDo(print());
    }


    private ResultActions 검색_키워드_목록_요청() throws Exception {
        return mockMvc.perform(get("/api/v1/places/search/list"))
                      .andDo(print());
    }
}
