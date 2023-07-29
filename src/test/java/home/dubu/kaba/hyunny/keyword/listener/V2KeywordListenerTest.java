package home.dubu.kaba.hyunny.keyword.listener;

import home.dubu.kaba.hyunny.common.KeywordEvent;
import home.dubu.kaba.hyunny.keyword.service.V2KeywordService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class V2KeywordListenerTest {

    @Test
    void listenKeywordEvent() {

        var keywordService = mock(V2KeywordService.class);
        var sut = new V2KeywordListener(keywordService);


        sut.listenKeywordEvent(KeywordEvent.of("짜장"));


        verify(keywordService,times(1)).addSearchKeywordHistory("짜장");
    }
}