package home.dubu.kaba.hyunny.search.domain.external;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class V2NaPlace {
    private String title;
    private String address;
    private String roadAddress;
}
