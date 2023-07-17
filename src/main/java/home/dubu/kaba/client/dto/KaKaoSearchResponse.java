package home.dubu.kaba.client.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KaKaoSearchResponse {
    private List<Document> documents;

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
    }
}
