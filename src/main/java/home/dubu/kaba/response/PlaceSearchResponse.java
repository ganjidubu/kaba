package home.dubu.kaba.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlaceSearchResponse {
    private List<Place> places;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Place {
        private String title;
        private String address;
    }
}
