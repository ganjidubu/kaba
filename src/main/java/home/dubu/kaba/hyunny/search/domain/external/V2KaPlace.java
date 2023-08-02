package home.dubu.kaba.hyunny.search.domain.external;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class V2KaPlace {
    @JsonAlias("place_name")
    private String title;
    @JsonAlias("address_name")
    private String address;
    @JsonAlias("road_address_name")
    private String roadAddress;
}
