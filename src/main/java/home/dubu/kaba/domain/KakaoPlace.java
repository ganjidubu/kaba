package home.dubu.kaba.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KakaoPlace {
    private String placeName;
    private String addressName;
    private String roadAddressName;
}
