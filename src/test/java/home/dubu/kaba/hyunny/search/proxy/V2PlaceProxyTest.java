package home.dubu.kaba.hyunny.search.proxy;

import home.dubu.kaba.hyunny.search.config.V2ApiProperties;
import home.dubu.kaba.hyunny.search.domain.external.V2KaDocument;
import home.dubu.kaba.hyunny.search.domain.external.V2KaPlace;
import home.dubu.kaba.hyunny.search.domain.external.V2NaItems;
import home.dubu.kaba.hyunny.search.domain.external.V2NaPlace;
import home.dubu.kaba.hyunny.search.proxy.client.KaClient;
import home.dubu.kaba.hyunny.search.proxy.client.NaClient;
import home.dubu.kaba.hyunny.search.proxy.impl.V2PlaceProxyImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class V2PlaceProxyTest {

    V2ApiProperties apiProperties;
    KaClient kaClient;
    NaClient naClient;
    V2PlaceProxy sut;

    @BeforeEach
    void setUp() {
        apiProperties = mock(V2ApiProperties.class);
        kaClient = mock(KaClient.class);
        naClient = mock(NaClient.class);
        sut = new V2PlaceProxyImpl(apiProperties, kaClient, naClient);
    }

    @Test
    void getPlacesInKaByKeyword() {

        when(apiProperties.getKaToken()).thenReturn("ka-api-token");
        when(kaClient.search("KakaoAK ka-api-token", "짜장"))
                .thenReturn(
                        V2KaDocument.builder()
                                .documents(
                                        List.of(
                                                V2KaPlace.builder()
                                                        .title("신의 짜장면")
                                                        .address("서울시 도봉구 창동")
                                                        .roadAddress("서울시 도봉구 노해로")
                                                        .build()
                                        )
                                )
                                .build()

                );


        var result = sut.getPlacesInKaByKeyword("짜장");


        assertEquals("신의 짜장면", result.get(0).getTitle());
        assertEquals("서울시 도봉구 창동", result.get(0).getAddress());
    }

    @Test
    void getPlacesInNaByKeyword() {

        when(apiProperties.getNaClientId()).thenReturn("na-client-id");
        when(apiProperties.getNaClientSecret()).thenReturn("na-client-secret");
        when(naClient.search("na-client-id", "na-client-secret", "짜장"))
                .thenReturn(
                        V2NaItems.builder()
                                .items(
                                        List.of(
                                                V2NaPlace.builder()
                                                        .title("신의 짜장면")
                                                        .address("서울시 도봉구 창동")
                                                        .roadAddress("서울시 도봉구 노해로")
                                                        .build()
                                        )
                                )
                                .build()
                );


        var result = sut.getPlacesInNaByKeyword("짜장");


        assertEquals("신의 짜장면", result.get(0).getTitle());
        assertEquals("서울시 도봉구 창동", result.get(0).getAddress());
    }
}