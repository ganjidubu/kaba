package home.dubu.kaba.client.dto;

import home.dubu.kaba.domain.NaverPlace;
import home.dubu.kaba.domain.NaverPlaces;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NaverPlaceSearchResponse {
    private List<Item> items;


    public NaverPlaces toDomain() {
        return new NaverPlaces(items.stream()
                                    .map(Item::toDomain)
                                    .collect(Collectors.toList()));
    }


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Item {
        private String title;
        private String address;
        private String roadAddress;


        public NaverPlace toDomain() {
            return new NaverPlace(title, address, roadAddress);
        }
    }
}
