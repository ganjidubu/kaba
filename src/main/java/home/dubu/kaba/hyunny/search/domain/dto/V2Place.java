package home.dubu.kaba.hyunny.search.domain.dto;

import home.dubu.kaba.hyunny.search.domain.external.V2KaPlace;
import home.dubu.kaba.hyunny.search.domain.external.V2NaPlace;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class V2Place {
    private String title;
    private String address;

    public static V2Place of(V2KaPlace v2KaPlace) {
        return V2Place.builder()
                .title(v2KaPlace.getTitle())
                .address(v2KaPlace.getAddress())
                .build();
    }

    public static V2Place of(V2NaPlace v2NaPlace) {
        return V2Place.builder()
                .title(v2NaPlace.getTitle())
                .address(v2NaPlace.getAddress())
                .build();
    }
}
