package home.dubu.kaba.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class KakaoPlaces {
    private List<KakaoPlace> kakaoPlaces;


    public Places getCommonPlaces(NaverPlaces naverPlaces, int maxSearchSize) {
        return new Places(kakaoPlaces.stream()
                                     .filter(kakaoPlace -> naverPlaces.hasCommonPlace(getSlicedAddress(kakaoPlace.getAddressName())))
                                     .map(Place::from)
                                     .limit(maxSearchSize)
                                     .collect(Collectors.toList()));
    }


    public Places getKakaoPlacesExceptCommon(Places places, int maxSearchSize) {
        return new Places(kakaoPlaces.stream()
                                     .filter(kakaoPlace -> !places.hasSamePlace(getSlicedAddress(kakaoPlace.getAddressName())))
                                     .map(Place::from)
                                     .limit(maxSearchSize)
                                     .collect(Collectors.toList()));
    }


    private String getSlicedAddress(String addressName) {
        String[] splits = addressName.split(" ");
        splits = Arrays.copyOfRange(splits, 1, splits.length);
        return String.join(" ", splits);
    }
}
