package home.dubu.kaba.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NaverPlaces {
    private List<NaverPlace> naverPlaces;


    public boolean hasCommonPlace(String addressName) {
        return naverPlaces.stream()
                          .anyMatch(naverPlace -> naverPlace.hasCommonPlace(addressName));
    }


    public Places getNaverPlacesExceptCommon(Places places, int maxSearchSize) {
        return new Places(naverPlaces.stream()
                                     .filter(naverPlace -> !places.hasSamePlace(naverPlace.getAddress()))
                                     .map(Place::from)
                                     .limit(maxSearchSize)
                                     .collect(Collectors.toList()));
    }
}
