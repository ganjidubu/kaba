package home.dubu.kaba.hyunny.keyword.listener;

import home.dubu.kaba.hyunny.common.KeywordEvent;
import home.dubu.kaba.hyunny.keyword.service.V2KeywordService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class V2KeywordListener {

    private final V2KeywordService keywordService;

    public V2KeywordListener(V2KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    @Async
    @EventListener
    public void listenKeywordEvent(KeywordEvent keywordEvent) {
        keywordService.addSearchKeywordHistory(keywordEvent.getKeyword());
    }
}
