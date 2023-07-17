package home.dubu.kaba.client;

import home.dubu.kaba.client.dto.KaKaoSearchResponse;
import home.dubu.kaba.config.KakaoProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Component
public class KakaoClient {
    private static final int SIZE = 10;
    private final KakaoProperties properties;


    public KaKaoSearchResponse search(String keyword) {
        try {
            WebClient webClient = WebClient.create(properties.getUrl());
            return webClient.get()
                            .uri(uriBuilder -> uriBuilder
                                .path("/search/keyword.json")
                                .queryParam("query", keyword)
                                .queryParam("size", SIZE)
                                .build())
                            .header("Authorization", "KakaoAK " + properties.getApiKey())
                            .accept(MediaType.APPLICATION_JSON)
                            .retrieve()
                            .bodyToMono(KaKaoSearchResponse.class)
                            .block();
        } catch (Exception e) {
            // TODO 커스텀 익셉션으로 바꿀 것
            throw new RuntimeException(e.getMessage());
        }
    }
}
