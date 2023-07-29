package home.dubu.kaba.hyunny.search.proxy.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.cloud.contract.spec.internal.MediaTypes.APPLICATION_JSON;

@SpringBootTest(
        properties = {"api.kakao.url=http://localhost:${wiremock.server.port}"}
)
@AutoConfigureWireMock(port = 0)
class KaClientIT {

    @Autowired
    KaClient sut;

    @Test
    void search() {

        var expectedResult = "{\"documents\": [{\"place_name\": \"신의 짜장면\", \"address_name\": \"서울시 도봉구 창동\", \"road_address_name\": \"서울시 도봉구 노해로\"}]}";
        stubFor(get(urlPathEqualTo("/search/keyword.json"))
                .withQueryParam("query", equalTo("짜장"))
                .withQueryParam("size", equalTo("10"))
                .withHeader("Authorization", equalTo("KakaoAK kakao-api-key"))
                .willReturn(
                        aResponse().withStatus(200)
                                .withHeader("Content-Type", APPLICATION_JSON)
                                .withBody(expectedResult)
                ));


        var result = sut.search("KakaoAK kakao-api-key", "짜장");


        assertEquals("신의 짜장면", result.getDocuments().get(0).getTitle());
        assertEquals("서울시 도봉구 창동", result.getDocuments().get(0).getAddress());
        assertEquals("서울시 도봉구 노해로", result.getDocuments().get(0).getRoadAddress());
    }
}