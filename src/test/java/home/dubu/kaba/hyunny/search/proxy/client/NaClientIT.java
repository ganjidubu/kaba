package home.dubu.kaba.hyunny.search.proxy.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.cloud.contract.spec.internal.MediaTypes.APPLICATION_JSON;

@SpringBootTest(
        properties = {"api.naver.url=http://localhost:${wiremock.server.port}"}
)
@AutoConfigureWireMock(port = 0)
class NaClientIT {

    @Autowired
    NaClient sut;

    @Test
    void search() {

        var expectedResult = "{\"items\": [{\"title\": \"신의 짜장면\", \"address\": \"서울시 도봉구 창동\", \"roadAddress\": \"서울시 도봉구 노해로\"}]}";
        stubFor(get(urlPathEqualTo("/local.json"))
                .withQueryParam("query", equalTo("짜장"))
                .withQueryParam("display", equalTo("5"))
                .withHeader("X-Naver-Client-Id", equalTo("naver-client-id"))
                .withHeader("X-Naver-Client-Secret", equalTo("naver-client-secret"))
                .willReturn(
                        aResponse().withStatus(200)
                                .withHeader("Content-Type", APPLICATION_JSON)
                                .withBody(expectedResult)
                ));


        var result = sut.search("naver-client-id", "naver-client-secret", "짜장");


        assertEquals("신의 짜장면", result.getItems().get(0).getTitle());
        assertEquals("서울시 도봉구 창동", result.getItems().get(0).getAddress());
        assertEquals("서울시 도봉구 노해로", result.getItems().get(0).getRoadAddress());
    }
}