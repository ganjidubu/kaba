package home.dubu.kaba.hyunny.keyword.service;

import home.dubu.kaba.hyunny.keyword.domain.dto.V2HottestKeyword;

import java.util.List;

public interface V2KeywordService {
    List<V2HottestKeyword> getTopFiveHottestKeywords();
    void addSearchKeywordHistory(String keyword);
}
