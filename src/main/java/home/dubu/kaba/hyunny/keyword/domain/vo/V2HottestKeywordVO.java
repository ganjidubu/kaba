package home.dubu.kaba.hyunny.keyword.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class V2HottestKeywordVO {
    private String keyword;
    private long count;

    public static V2HottestKeywordVO of(V2HottestKeywordProjection projection) {
        return new V2HottestKeywordVO(projection.getKeyword(), projection.getCounter());
    }
}
