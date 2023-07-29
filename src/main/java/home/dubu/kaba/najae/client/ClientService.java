package home.dubu.kaba.najae.client;

import home.dubu.kaba.najae.domain.Places;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ClientService {
    private final NaverClient naverClient;
    private final KakaoClient kakaoClient;


    public Places searchByKakao(String keyword) {
        return kakaoClient.search(keyword);
    }


    public Places searchByNaver(String keyword) {
        return naverClient.search(keyword);
    }
}
