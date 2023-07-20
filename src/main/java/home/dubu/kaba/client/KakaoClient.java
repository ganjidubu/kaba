package home.dubu.kaba.client;

import home.dubu.kaba.client.dto.KakaoPlaceSearchResponse;
import home.dubu.kaba.config.KakaoProperties;
import home.dubu.kaba.domain.Places;
import home.dubu.kaba.exception.KabaException;
import home.dubu.kaba.exception.ResultCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Component
public class KakaoClient implements ExternalClient {
    private static final int SIZE = 10;
    private final KakaoProperties properties;


    public Places search(String keyword) {
        WebClient webClient = WebClient.create(properties.getUrl());
        var response = webClient.get()
                                .uri(uriBuilder -> uriBuilder
                                    .path("/search/keyword.json")
                                    .queryParam("query", keyword)
                                    .queryParam("size", SIZE)
                                    .build())
                                .header("Authorization", "KakaoAK " + properties.getApiKey())
                                .accept(MediaType.APPLICATION_JSON)
                                .retrieve()
                                .bodyToMono(KakaoPlaceSearchResponse.class)
                                .block();
        if (response == null) {
            throw new KabaException(ResultCode.CLIENT_INTERNAL_SERVER_ERROR);
        }

        return response.toDomain();
    }
}
