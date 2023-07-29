package home.dubu.kaba.hyunny.search.event;

import home.dubu.kaba.hyunny.keyword.service.V2KeywordService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@SpringBootTest
class V2SearchEventPublisherIT {

    @MockBean
    V2KeywordService keywordService;
    @Autowired
    V2SearchEventPublisher sut;

    @Test
    void saveSearchedKeyword_thenKeywordServiceReceiveEvent() throws InterruptedException {

        sut.saveSearchedKeyword("짜장");
        Thread.sleep(150);


        verify(keywordService, Mockito.times(1)).addSearchKeywordHistory("짜장");
    }

    @Test
    void throwExceptionFromKeywordService_saveSearchedKeyword_thenNothingThrowable() throws InterruptedException {

        doThrow(new RuntimeException("add history error"))
                .when(keywordService).addSearchKeywordHistory("짜장");


        sut.saveSearchedKeyword("짜장");
        Thread.sleep(150);
    }
}