package home.dubu.kaba.service;

import home.dubu.kaba.client.KakaoClient;
import home.dubu.kaba.client.NaverClient;
import home.dubu.kaba.client.dto.KaKaoSearchResponse;
import home.dubu.kaba.client.dto.NaverSearchResponse;
import home.dubu.kaba.dto.response.PlaceSearchResponse;
import home.dubu.kaba.dto.response.SearchListResponse;
import home.dubu.kaba.entity.Search;
import home.dubu.kaba.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class SearchService {
    private static final int SEARCH_MAX_SIZE = 10;
    private static final int MAX_PAGE_SIZE = 10;

    private final KakaoClient kakaoClient;
    private final NaverClient naverClient;
    private final SearchRepository repository;


    // TODO 테스트 케이스 더 가져와서 검증 해볼 것
    @Transactional
    public PlaceSearchResponse searchPlace(String keyword) {
        var kakaoSearchResponse = kakaoClient.search(keyword);
        var naverSearchResponse = naverClient.search(keyword);

        var kakaoDocuments = kakaoSearchResponse.getDocuments();
        var naverItems = naverSearchResponse.getItems();

        List<PlaceSearchResponse.Place> response = fillInCommonResponse(kakaoDocuments, naverItems);
        fillInKakaoSearchResponse(response, kakaoDocuments);
        fillInNaverSearchResponse(response, naverItems);

        saveSearchKeyword(keyword);

        return new PlaceSearchResponse(response);
    }


    private void saveSearchKeyword(String keyword) {
        try {
            Optional<Search> retrievedSearch = repository.findByKeyword(keyword);
            if (retrievedSearch.isPresent()) {
                retrievedSearch.get().countUp();
            } else {
                repository.save(new Search(keyword, 1));
            }
        } catch (Exception e) {
            System.out.println("save 실패");
        }
    }


    public SearchListResponse findSearchList() {
        var sort = Sort.by("count").descending();
        var pageable = PageRequest.of(0, MAX_PAGE_SIZE, sort);
        var result = repository.findAll(pageable);

        return SearchListResponse.from(result.getContent());
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
            if (response.size() == SEARCH_MAX_SIZE) {
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
        for (NaverSearchResponse.Item naverItem : naverItems) {
            if (response.size() == SEARCH_MAX_SIZE) {
                break;
            }
            response.add(new PlaceSearchResponse.Place(naverItem.getTitle(), naverItem.getAddress()));
        }
    }


    private void fillInKakaoSearchResponse(List<PlaceSearchResponse.Place> response, List<KaKaoSearchResponse.Document> kakaoDocuments) {
        for (KaKaoSearchResponse.Document kakaoDocument : kakaoDocuments) {
            if (response.size() == SEARCH_MAX_SIZE) {
                break;
            }
            response.add(new PlaceSearchResponse.Place(kakaoDocument.getPlaceName(), kakaoDocument.getAddressName()));
        }
    }
}
