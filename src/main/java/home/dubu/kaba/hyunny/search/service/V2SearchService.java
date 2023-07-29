package home.dubu.kaba.hyunny.search.service;

import home.dubu.kaba.hyunny.search.domain.dto.V2Place;

import java.util.List;

public interface V2SearchService {
    List<V2Place> getPlacesByKeyword(String keyword);
}
