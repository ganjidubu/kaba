package home.dubu.kaba.hyunny.search.service;

import home.dubu.kaba.hyunny.search.domain.dto.V2Place;
import home.dubu.kaba.hyunny.search.event.V2SearchEventPublisher;
import home.dubu.kaba.hyunny.search.proxy.V2PlaceProxy;
import home.dubu.kaba.hyunny.search.service.impl.V2SearchServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class V2SearchServiceTest {

    V2SearchEventPublisher searchEventPublisher;
    V2PlaceProxy placeProxy;
    V2SearchService sut;

    @BeforeEach
    void setUp() {
        searchEventPublisher = mock(V2SearchEventPublisher.class);
        placeProxy = mock(V2PlaceProxy.class);
        sut = new V2SearchServiceImpl(searchEventPublisher, placeProxy);
    }

    V2Place getSamplePlace(String title, String address) {
        return V2Place.builder()
                .title(title)
                .address(address)
                .build();
    }

    @Test
    @DisplayName("키워드에 해당하는 모든 장소를 검색할 수 있다.")
    void getPlacesByKeyword() {

        when(placeProxy.getPlacesInKaByKeyword("짜장면"))
                .thenReturn(
                        List.of(getSamplePlace("신의 짜장면", "서울시 도봉구"))
                );
        when(placeProxy.getPlacesInNaByKeyword("짜장면"))
                .thenReturn(
                        List.of(getSamplePlace("쟁반 짜장면", "서울시 서대문구"))
                );


        var result = sut.getPlacesByKeyword("짜장면");


        assertEquals("신의 짜장면", result.get(0).getTitle());
        assertEquals("서울시 도봉구", result.get(0).getAddress());
        assertEquals("쟁반 짜장면", result.get(1).getTitle());
        assertEquals("서울시 서대문구", result.get(1).getAddress());
    }

    @Test
    @DisplayName("주소가 같지 않은 장소들만 검색할 수 있다.")
    void existsSameAddress_getPlacesByKeyword_thenRemoveDuplicatedPlaces() {

        when(placeProxy.getPlacesInKaByKeyword("짜장면"))
                .thenReturn(
                        List.of(
                                getSamplePlace("신의 짜장면", "서울시 도봉구"),
                                getSamplePlace("중화반점", "서울시 종로구 1동 1번지 1층")
                        )
                );
        when(placeProxy.getPlacesInNaByKeyword("짜장면"))
                .thenReturn(
                        List.of(
                                getSamplePlace("쟁반 짜장면", "서울시 서대문구"),
                                getSamplePlace("신성반점", "서울시 종로구 1동 1번지 1층")
                        )
                );


        var result = sut.getPlacesByKeyword("짜장면");


        assertEquals("신의 짜장면", result.get(0).getTitle());
        assertEquals("서울시 도봉구", result.get(0).getAddress());
        assertEquals("중화반점", result.get(1).getTitle());
        assertEquals("서울시 종로구 1동 1번지 1층", result.get(1).getAddress());
        assertEquals("쟁반 짜장면", result.get(2).getTitle());
        assertEquals("서울시 서대문구", result.get(2).getAddress());
    }

    @Test
    @DisplayName("장소 검색 성공 후 검색 키워드를 이벤트 발행을 통해 외부로 전달한다.")
    void publishEventAfterSearchPlaces() {

        sut.getPlacesByKeyword("짜장면");


        verify(searchEventPublisher, times(1)).saveSearchedKeyword("짜장면");
    }
}