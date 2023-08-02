package home.dubu.kaba.hyunny.search.event;

import home.dubu.kaba.hyunny.common.KeywordEvent;
import home.dubu.kaba.hyunny.search.event.impl.V2SearchEventPublisherImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class V2SearchEventPublisherTest {

    @Test
    void saveSearchedKeyword() {

        var applicationContext = mock(ApplicationContext.class);
        var sut = new V2SearchEventPublisherImpl(applicationContext);


        sut.saveSearchedKeyword("짜장");


        var captor = ArgumentCaptor.forClass(KeywordEvent.class);
        verify(applicationContext, times(1)).publishEvent(captor.capture());
        var result = captor.getValue();
        assertEquals("짜장", result.getKeyword());
    }
}