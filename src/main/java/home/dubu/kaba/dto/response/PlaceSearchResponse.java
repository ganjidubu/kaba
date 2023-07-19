package home.dubu.kaba.dto.response;

import home.dubu.kaba.domain.Place;
import home.dubu.kaba.domain.Places;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlaceSearchResponse {
    private List<Result> places;


    public static PlaceSearchResponse from(Places places) {
        return new PlaceSearchResponse(places.getPlaces().stream()
                                             .map(Result::from)
                                             .collect(Collectors.toList()));
    }


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Result {
        private String title;
        private String address;


        public static Result from(Place place) {
            return new Result(place.getTitle(), place.getAddress());
        }
    }
}
