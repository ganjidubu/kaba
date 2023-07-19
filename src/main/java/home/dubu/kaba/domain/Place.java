package home.dubu.kaba.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Place {
    private String title;
    private String address;


    public static Place from(KakaoPlace kakaoPlace) {
        return new Place(kakaoPlace.getPlaceName(), kakaoPlace.getAddressName());
    }


    public static Place from(NaverPlace naverPlace) {
        return new Place(naverPlace.getTitle(), naverPlace.getAddress());
    }


    public boolean isSamePlace(String targetAddress) {
        var sourceSplits = address.split(" ");
        var targetSplits = targetAddress.split(" ");

        if (targetSplits.length > sourceSplits.length) {
            sourceSplits = Arrays.copyOfRange(sourceSplits, 1, sourceSplits.length);
            targetSplits = Arrays.copyOfRange(targetSplits, 1, sourceSplits.length);
            var address1 = String.join(" ", sourceSplits);
            var address2 = String.join(" ", targetSplits);

            return address1.contains(address2);
        }

        return address.contains(targetAddress);
    }
}
