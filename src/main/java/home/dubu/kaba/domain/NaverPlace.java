package home.dubu.kaba.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NaverPlace {
    private String title;
    private String address;
    private String roadAddress;


    public boolean hasCommonPlace(String addressName) {
        return address.contains(addressName);
    }
}
