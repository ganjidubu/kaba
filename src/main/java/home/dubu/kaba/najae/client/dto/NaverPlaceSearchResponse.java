package home.dubu.kaba.najae.client.dto;

import home.dubu.kaba.najae.domain.Place;
import home.dubu.kaba.najae.domain.Places;
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


    public Places toDomain() {
        return new Places(items.stream()
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


        public Place toDomain() {
            return new Place(title, address);
        }
    }
}
