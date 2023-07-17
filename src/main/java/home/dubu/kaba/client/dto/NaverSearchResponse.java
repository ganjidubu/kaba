package home.dubu.kaba.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NaverSearchResponse {
    private List<Item> items;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Item {
        private String title;
        private String address;
        private String roadAddress;
    }
}
