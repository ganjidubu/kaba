package home.dubu.kaba.najae.dto.response;

import home.dubu.kaba.najae.domain.Place;
import home.dubu.kaba.najae.domain.Places;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlaceSearchResponse {
    private List<Result> results;


    public static PlaceSearchResponse from(Places places) {
        return new PlaceSearchResponse(places.getPlaceList().stream()
                                             .map(Result::from)
                                             .collect(Collectors.toList()));
    }


    @EqualsAndHashCode
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
