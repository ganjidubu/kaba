package home.dubu.kaba.service;

import home.dubu.kaba.client.KakaoClient;
import home.dubu.kaba.client.NaverClient;
import home.dubu.kaba.client.dto.KaKaoSearchResponse;
import home.dubu.kaba.client.dto.NaverSearchResponse;
import home.dubu.kaba.response.PlaceSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SearchService {
    private static final int MAX_SIZE = 10;

    private final KakaoClient kakaoClient;
    private final NaverClient naverClient;


    public PlaceSearchResponse searchPlace(String keyword) {
        var kakaoSearchResponse = kakaoClient.search(keyword);
        var naverSearchResponse = naverClient.search(keyword);

        var kakaoDocuments = kakaoSearchResponse.getDocuments();
        var naverItems = naverSearchResponse.getItems();

        // 같은 것 있나 비교해서 같은 것 있으면 먼저 담기
        List<PlaceSearchResponse.Place> response = fillInCommonResponse(kakaoDocuments, naverItems);

        // TODO 테스트 케이스 더 가져와서 검증 해볼 것
        fillInKakaoSearchResponse(response, kakaoDocuments);
        fillInNaverSearchResponse(response, naverItems);

        return new PlaceSearchResponse(response);
    }


    private List<PlaceSearchResponse.Place> fillInCommonResponse(List<KaKaoSearchResponse.Document> kakaoDocuments, List<NaverSearchResponse.Item> naverItems) {
        List<PlaceSearchResponse.Place> response = new ArrayList<>();
        for (int i = 0; i < kakaoDocuments.size(); i++) {
            var kakaoDocument = kakaoDocuments.get(i);
            var kakaoAddress = kakaoDocument.getAddressName();
            String slicedKakaoAddress = getSlicedKakaoAddress(kakaoAddress);

            for (int j = 0; j < naverItems.size(); j++) {
                var naverAddress = naverItems.get(j).getAddress();
                if (naverAddress.contains(slicedKakaoAddress)) {
                    response.add(new PlaceSearchResponse.Place(kakaoDocument.getPlaceName(), kakaoDocument.getAddressName()));
                    naverItems.remove(j);
                    kakaoDocuments.remove(i--);
                    break;
                }
            }
            if (response.size() == MAX_SIZE) {
                break;
            }
        }
        return response;
    }


    /**
     * 네이버 지역 주소와 비교했을 때 ~시 부분 빼고 일치하므로 ~시 부분 slice
     *
     * @param kakaoPlaceAddress
     * @return
     */
    private String getSlicedKakaoAddress(String kakaoPlaceAddress) {
        var splits = kakaoPlaceAddress.split(" ");
        splits = Arrays.copyOfRange(splits, 1, splits.length);
        return String.join(" ", splits);
    }


    private void fillInNaverSearchResponse(List<PlaceSearchResponse.Place> response, List<NaverSearchResponse.Item> naverItems) {
        for (int i = 0; i < naverItems.size(); i++) {
            if (response.size() == MAX_SIZE) {
                break;
            }
            response.add(new PlaceSearchResponse.Place(naverItems.get(i).getTitle(), naverItems.get(i).getAddress()));
        }
    }


    private void fillInKakaoSearchResponse(List<PlaceSearchResponse.Place> response, List<KaKaoSearchResponse.Document> kakaoDocuments) {
        for (int i = 0; i < kakaoDocuments.size(); i++) {
            if (response.size() == MAX_SIZE) {
                break;
            }
            response.add(new PlaceSearchResponse.Place(kakaoDocuments.get(i).getPlaceName(), kakaoDocuments.get(i).getAddressName()));
        }
    }
}
