package home.dubu.kaba.client;

import home.dubu.kaba.client.dto.KaKaoSearchResponse;
import home.dubu.kaba.client.dto.NaverSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ClientService {
    private final NaverClient naverClient;
    private final KakaoClient kakaoClient;


    public KaKaoSearchResponse searchByKakao(String keyword) {
        return kakaoClient.search(keyword);
    }


    public NaverSearchResponse searchByNaver(String keyword) {
        return naverClient.search(keyword);
    }
}
