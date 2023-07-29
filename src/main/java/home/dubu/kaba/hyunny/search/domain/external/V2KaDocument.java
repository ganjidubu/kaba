package home.dubu.kaba.hyunny.search.domain.external;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class V2KaDocument {
    List<V2KaPlace> documents;
}
