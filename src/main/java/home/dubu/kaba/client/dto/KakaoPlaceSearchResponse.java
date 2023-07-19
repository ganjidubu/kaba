package home.dubu.kaba.client.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import home.dubu.kaba.domain.KakaoPlace;
import home.dubu.kaba.domain.KakaoPlaces;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KakaoPlaceSearchResponse {
    private List<Document> documents;


    public KakaoPlaces toDomain() {
        return new KakaoPlaces(documents.stream()
                                        .map(Document::toDomain)
                                        .collect(Collectors.toList()));
    }


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Document {
        @JsonAlias("place_name")
        private String placeName;
        @JsonAlias("address_name")
        private String addressName;
        @JsonAlias("road_address_name")
        private String roadAddressName;


        public KakaoPlace toDomain() {
            return new KakaoPlace(placeName, addressName, roadAddressName);
        }
    }
}
