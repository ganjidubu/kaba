package home.dubu.kaba.hyunny.search.event.impl;

import home.dubu.kaba.hyunny.common.KeywordEvent;
import home.dubu.kaba.hyunny.search.event.V2SearchEventPublisher;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class V2SearchEventPublisherImpl implements V2SearchEventPublisher {

    private final ApplicationContext applicationContext;

    public V2SearchEventPublisherImpl(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void saveSearchedKeyword(String keyword) {
        applicationContext.publishEvent(
                KeywordEvent.of(keyword)
        );
    }
}
