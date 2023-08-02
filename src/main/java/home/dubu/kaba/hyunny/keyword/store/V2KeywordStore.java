package home.dubu.kaba.hyunny.keyword.store;

import home.dubu.kaba.hyunny.keyword.domain.vo.V2HottestKeywordVO;

import java.util.List;

public interface V2KeywordStore {

    List<V2HottestKeywordVO> getTopFiveHottestKeywords();

    void addSearchKeywordHistory(String keyword);
}
