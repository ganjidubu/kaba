package home.dubu.kaba.hyunny.keyword.domain.dto;

import home.dubu.kaba.hyunny.keyword.domain.vo.V2HottestKeywordVO;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class V2HottestKeyword {
    private String keyword;
    private long count;

    public static V2HottestKeyword of(V2HottestKeywordVO v2HottestKeywordVO) {
        return V2HottestKeyword.builder()
                .keyword(v2HottestKeywordVO.getKeyword())
                .count(v2HottestKeywordVO.getCount())
                .build();
    }
}
