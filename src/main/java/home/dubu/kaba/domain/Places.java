package home.dubu.kaba.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
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


    public boolean isLimitSize(int maxSearchSize) {
        return placeList.size() == maxSearchSize;
    }


    public Places getCommonPlaces(Places places, int maxSearchSize) {
        return new Places(placeList.stream()
                                   .filter(place -> places.hasSamePlace(getSlicedAddress(place.getAddress())))
                                   .limit(maxSearchSize)
                                   .collect(Collectors.toList()));
    }


    public Places getKakaoPlacesExceptCommon(Places places, int maxSearchSize) {
        return new Places(placeList.stream()
                                   .filter(place -> !places.hasSamePlace(getSlicedAddress(place.getAddress())))
                                   .limit(maxSearchSize)
                                   .collect(Collectors.toList()));
    }


    public Places getNaverPlacesExceptCommon(Places places, int maxSearchSize) {
        return new Places(placeList.stream()
                                   .filter(place -> !places.hasSamePlace(place.getAddress()))
                                   .limit(maxSearchSize)
                                   .collect(Collectors.toList()));
    }


    public boolean hasSamePlace(String address) {
        return placeList.stream()
                        .anyMatch(place -> place.isSamePlace(address));
    }


    private String getSlicedAddress(String addressName) {
        String[] splits = addressName.split(" ");
        splits = Arrays.copyOfRange(splits, 1, splits.length);
        return String.join(" ", splits);
    }

}
