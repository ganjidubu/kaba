package home.dubu.kaba.hyunny.keyword.store.impl;

import home.dubu.kaba.hyunny.keyword.domain.entity.KeywordEntity;
import home.dubu.kaba.hyunny.keyword.domain.vo.V2HottestKeywordVO;
import home.dubu.kaba.hyunny.keyword.store.V2KeywordStore;
import home.dubu.kaba.hyunny.keyword.store.repository.V2KeywordRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class JpaV2KeywordStore implements V2KeywordStore {

    private final V2KeywordRepository keywordRepository;

    public JpaV2KeywordStore(V2KeywordRepository keywordRepository) {
        this.keywordRepository = keywordRepository;
    }

    @Override
    public List<V2HottestKeywordVO> getTopFiveHottestKeywords() {
        return keywordRepository.findTopFiveHottestKeywords(Pageable.ofSize(5))
                .stream()
                .map(V2HottestKeywordVO::of)
                .collect(Collectors.toList());
    }

    @Override
    public void addSearchKeywordHistory(String keyword) {
        keywordRepository.save(
                KeywordEntity.builder()
                        .keyword(keyword)
                        .build()
        );
    }
}
