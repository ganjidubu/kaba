package home.dubu.kaba.najae.dto.response;

import home.dubu.kaba.najae.entity.Search;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SearchListResponse {
    private List<SearchDto> searchList;


    public static SearchListResponse from(List<Search> searchList) {
        return new SearchListResponse(searchList.stream()
                                                .map(SearchDto::from)
                                                .collect(Collectors.toList()));
    }


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SearchDto {
        private String keyword;
        private int count;


        public static SearchDto from(Search search) {
            return new SearchDto(search.getKeyword(), search.getCount());
        }
    }
}
