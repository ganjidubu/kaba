package home.dubu.kaba.najae.service;

import home.dubu.kaba.najae.client.ClientService;
import home.dubu.kaba.najae.domain.Place;
import home.dubu.kaba.najae.domain.Places;
import home.dubu.kaba.najae.dto.response.PlaceSearchResponse;
import home.dubu.kaba.najae.entity.Search;
import home.dubu.kaba.najae.repository.SearchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    private ClientService clientService;
    @Mock
    private SearchRepository searchRepository;
    @Mock
    private SearchSaveService searchSaveService;
    private SearchService sut;


    @BeforeEach
    void setUp() {
        sut = new SearchService(clientService, searchSaveService, searchRepository);
    }


    @DisplayName("장소 검색")
    @Test
    void searchByKeyword() {
        // given
        when(clientService.searchByKakao(anyString())).thenReturn(
            new Places(List.of(
                new Place("title1", "address1"),
                new Place("title2", "address2"),
                new Place("title3", "address3"),
                new Place("title9", "address9"),
                new Place("title10", "address10"),
                new Place("title11", "address11"))));
        when(clientService.searchByNaver(anyString())).thenReturn(
            new Places(List.of(
                new Place("title1", "address1"),
                new Place("title2", "address2"),
                new Place("title3", "address3"),
                new Place("title4", "address4"),
                new Place("title5", "address5"),
                new Place("title6", "address6"),
                new Place("title7", "address7"),
                new Place("title8", "address8"),
                new Place("title9", "address9"),
                new Place("title10", "address10"),
                new Place("title11", "address11"))));

        // when
        var response = sut.searchByKeyword("title1");
        var target = List.of(
            new PlaceSearchResponse.Result("title1", "address1"),
            new PlaceSearchResponse.Result("title2", "address2"),
            new PlaceSearchResponse.Result("title3", "address3"),
            new PlaceSearchResponse.Result("title9", "address9"),
            new PlaceSearchResponse.Result("title10", "address10"),
            new PlaceSearchResponse.Result("title11", "address11"),
            new PlaceSearchResponse.Result("title4", "address4"),
            new PlaceSearchResponse.Result("title5", "address5"),
            new PlaceSearchResponse.Result("title6", "address6"),
            new PlaceSearchResponse.Result("title7", "address7"));

        // then
        assertThat(response.getResults()).hasSize(10);
        assertThat(response.getResults()).isEqualTo(target);
    }


    @DisplayName("검색 키워드 목록")
    @Test
    void findSearchList() {
        // given
        var searchList = List.of(new Search("곱창1", 1),
                                 new Search("곱창2", 2),
                                 new Search("곱창3", 3),
                                 new Search("곱창4", 4),
                                 new Search("곱창5", 5),
                                 new Search("곱창6", 6),
                                 new Search("곱창7", 7),
                                 new Search("곱창8", 8),
                                 new Search("곱창9", 9),
                                 new Search("곱창10", 10));
        int pageNumber = 0;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("count").descending());
        Page<Search> pageResult = new PageImpl<>(searchList, pageable, searchList.size());
        when(searchRepository.findAll(any(PageRequest.class))).thenReturn(pageResult);

        // when
        var response = sut.findSearchList();

        // then
        assertThat(response.getSearchList()).hasSize(10);
        assertThat(response.getSearchList().get(0).getKeyword()).isEqualTo("곱창1");
    }
}
