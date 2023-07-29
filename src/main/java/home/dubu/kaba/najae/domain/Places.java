package home.dubu.kaba.najae.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Places {
    private List<Place> placeList;


    public void addAll(Places places) {
        placeList.addAll(places.getPlaceList());
    }
    

    public Places getCommonPlaces(Places places, int maxSearchSize) {
        return new Places(placeList.stream()
                                   .filter(place -> places.hasSamePlace(place.getAddress()))
                                   .limit(maxSearchSize)
                                   .collect(Collectors.toList()));
    }


    public Places getPlacesExceptCommon(Places places, int maxSearchSize) {
        return new Places(placeList.stream()
                                   .filter(place -> !places.hasSamePlace(place.getAddress()))
                                   .limit(maxSearchSize)
                                   .collect(Collectors.toList()));
    }


    public boolean hasSamePlace(String address) {
        return placeList.stream()
                        .anyMatch(place -> place.isSamePlace(address));
    }


    public int size() {
        return placeList.size();
    }
}
