package home.dubu.kaba.hyunny.search.service.impl;

import home.dubu.kaba.hyunny.search.domain.dto.V2Place;
import home.dubu.kaba.hyunny.search.event.V2SearchEventPublisher;
import home.dubu.kaba.hyunny.search.proxy.V2PlaceProxy;
import home.dubu.kaba.hyunny.search.service.V2SearchService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class V2SearchServiceImpl implements V2SearchService {

    private final V2SearchEventPublisher searchEventPublisher;
    private final V2PlaceProxy placeProxy;

    public V2SearchServiceImpl(V2SearchEventPublisher searchEventPublisher, V2PlaceProxy placeProxy) {
        this.searchEventPublisher = searchEventPublisher;
        this.placeProxy = placeProxy;
    }

    private Collector<V2Place, ?, LinkedHashMap<String, V2Place>> mapByAddress() {
        return Collectors.toMap(
                V2Place::getAddress,
                Function.identity(),
                (oldVal, newVal) -> newVal,
                LinkedHashMap::new
        );
    }

    @Override
    public List<V2Place> getPlacesByKeyword(String keyword) {

        var result = new ArrayList<V2Place>();

        var placesFromKa = placeProxy.getPlacesInKaByKeyword(keyword);
        var mapByAddressFromNa = placeProxy.getPlacesInNaByKeyword(keyword)
                .stream()
                .collect(mapByAddress());

        for (V2Place place : placesFromKa) {
            var address = place.getAddress();
            mapByAddressFromNa.remove(address);
            result.add(place);
        }

        result.addAll(
                new ArrayList<>(mapByAddressFromNa.values())
        );

        searchEventPublisher.saveSearchedKeyword(keyword);

        return result;
    }
}
