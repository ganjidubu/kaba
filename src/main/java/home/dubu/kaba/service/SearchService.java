package home.dubu.kaba.service;

import home.dubu.kaba.client.ClientService;
import home.dubu.kaba.dto.response.PlaceSearchResponse;
import home.dubu.kaba.dto.response.SearchListResponse;
import home.dubu.kaba.entity.Search;
import home.dubu.kaba.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class SearchService {
    private static final int PLACE_SIZE_LIMIT = 10;
    private static final int PAGE_INDEX = 0;
    private static final int PAGE_SIZE = 10;
    private static final String SORT_PROPERTY = "count";

    private final ClientService clientService;
    private final SearchRepository repository;


    @Transactional
    public PlaceSearchResponse searchByKeyword(String keyword) {
        saveSearchKeyword(keyword);

        var kakaoPlaces = clientService.searchByKakao(keyword);
        var naverPlaces = clientService.searchByNaver(keyword);

        var places = kakaoPlaces.getCommonPlaces(naverPlaces, PLACE_SIZE_LIMIT);

        var restKakaoPlaces = kakaoPlaces.getPlacesExceptCommon(places, PLACE_SIZE_LIMIT - places.size());
        places.addAll(restKakaoPlaces);

        var restNaverPlaces = naverPlaces.getPlacesExceptCommon(places, PLACE_SIZE_LIMIT - places.size());
        places.addAll(restNaverPlaces);

        return PlaceSearchResponse.from(places);
    }


    public SearchListResponse findSearchList() {
        var sort = Sort.by(SORT_PROPERTY).descending();
        var pageable = PageRequest.of(PAGE_INDEX, PAGE_SIZE, sort);
        var result = repository.findAll(pageable);

        return SearchListResponse.from(result.getContent());
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
}
