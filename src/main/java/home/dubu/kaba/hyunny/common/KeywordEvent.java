package home.dubu.kaba.hyunny.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class KeywordEvent {
    private String keyword;

    public static KeywordEvent of(String keyword) {
        return new KeywordEvent(keyword);
    }
}
