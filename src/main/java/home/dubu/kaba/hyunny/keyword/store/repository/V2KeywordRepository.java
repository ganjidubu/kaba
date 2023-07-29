package home.dubu.kaba.hyunny.keyword.store.repository;

import home.dubu.kaba.hyunny.keyword.domain.entity.KeywordEntity;
import home.dubu.kaba.hyunny.keyword.domain.vo.V2HottestKeywordProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface V2KeywordRepository extends JpaRepository<KeywordEntity, Long> {

    @Query("SELECT k.keyword as keyword, count(k.id) as counter"
            + " FROM KeywordEntity k "
            + " GROUP BY k.keyword "
            + " ORDER BY counter desc"
    )
    List<V2HottestKeywordProjection> findTopFiveHottestKeywords();
}
