package home.dubu.kaba.client;

import home.dubu.kaba.client.dto.KakaoPlaceSearchResponse;
import home.dubu.kaba.client.dto.NaverPlaceSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ClientService {
    private final NaverClient naverClient;
    private final KakaoClient kakaoClient;


    public KakaoPlaceSearchResponse searchByKakao(String keyword) {
        return kakaoClient.search(keyword);
    }


    public NaverPlaceSearchResponse searchByNaver(String keyword) {
        return naverClient.search(keyword);
    }
}
