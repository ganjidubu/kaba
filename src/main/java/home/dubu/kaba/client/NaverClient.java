package home.dubu.kaba.client;

import home.dubu.kaba.client.dto.NaverPlaceSearchResponse;
import home.dubu.kaba.config.NaverProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Component
public class NaverClient {
    private final NaverProperties properties;


    public NaverPlaceSearchResponse search(String keyword) {
        try {
            WebClient webClient = WebClient.create(properties.getUrl());
            return webClient.get()
                            .uri(uriBuilder -> uriBuilder
                                .path("/local.json")
                                .queryParam("query", keyword)
                                .queryParam("display", 5)
                                .build())
                            .header("X-Naver-Client-Id", properties.getClientId())
                            .header("X-Naver-Client-Secret", properties.getSecret())
                            .accept(MediaType.APPLICATION_JSON)
                            .retrieve()
                            .bodyToMono(NaverPlaceSearchResponse.class)
                            .block();
        } catch (Exception e) {
            // TODO 커스텀 익셉션으로 바꿀 것
            throw new RuntimeException(e.getMessage());
        }
    }
}
