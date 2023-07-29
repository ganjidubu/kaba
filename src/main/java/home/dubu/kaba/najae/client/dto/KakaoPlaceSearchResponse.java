package home.dubu.kaba.najae.client.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
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
public class KakaoPlaceSearchResponse {
    private List<Document> documents;


    public Places toDomain() {
        return new Places(documents.stream()
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


        public Place toDomain() {
            return new Place(placeName, addressName);
        }
    }
}
