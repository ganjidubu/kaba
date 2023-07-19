package home.dubu.kaba.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Places {
    private List<Place> places;


    public boolean hasSamePlace(String address) {
        return places.stream()
                     .anyMatch(place -> place.isSamePlace(address));
    }


    public void addAll(Places searchs) {
        places.addAll(searchs.getPlaces());
    }


    public boolean isLimitSize(int maxSearchSize) {
        return places.size() == maxSearchSize;
    }
}
