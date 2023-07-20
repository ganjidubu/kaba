package home.dubu.kaba.client;

import home.dubu.kaba.client.dto.NaverPlaceSearchResponse;
import home.dubu.kaba.config.NaverProperties;
import home.dubu.kaba.domain.Places;
import home.dubu.kaba.exception.KabaException;
import home.dubu.kaba.exception.ResultCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Component
public class NaverClient implements ExternalClient {
    private final NaverProperties properties;


    public Places search(String keyword) {
        WebClient webClient = WebClient.create(properties.getUrl());
        var response = webClient.get()
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
        if (response == null) {
            throw new KabaException(ResultCode.CLIENT_INTERNAL_SERVER_ERROR);
        }

        return response.toDomain();
    }
}
