package home.dubu.kaba.hyunny.search.proxy;

import home.dubu.kaba.hyunny.search.domain.dto.V2Place;

import java.util.List;

public interface V2PlaceProxy {
    List<V2Place> getPlacesInKaByKeyword(String keyword);

    List<V2Place> getPlacesInNaByKeyword(String keyword);
}
