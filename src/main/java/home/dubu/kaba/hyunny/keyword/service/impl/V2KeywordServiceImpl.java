package home.dubu.kaba.hyunny.keyword.service.impl;

import home.dubu.kaba.hyunny.keyword.domain.dto.V2HottestKeyword;
import home.dubu.kaba.hyunny.keyword.service.V2KeywordService;
import home.dubu.kaba.hyunny.keyword.store.V2KeywordStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class V2KeywordServiceImpl implements V2KeywordService {

    private final V2KeywordStore keywordStore;

    public V2KeywordServiceImpl(V2KeywordStore keywordStore) {
        this.keywordStore = keywordStore;
    }

    @Override
    public List<V2HottestKeyword> getTopFiveHottestKeywords() {
        return keywordStore.getTopFiveHottestKeywords()
                .stream()
                .map(V2HottestKeyword::of)
                .collect(Collectors.toList());
    }

    @Override
    public void addSearchKeywordHistory(String keyword) {
        keywordStore.addSearchKeywordHistory(keyword);
    }
}
