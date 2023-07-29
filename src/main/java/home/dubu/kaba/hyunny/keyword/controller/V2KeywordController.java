package home.dubu.kaba.hyunny.keyword.controller;

import home.dubu.kaba.hyunny.keyword.domain.dto.V2HottestKeyword;
import home.dubu.kaba.hyunny.keyword.service.V2KeywordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v2")
public class V2KeywordController {

    private final V2KeywordService keywordService;

    public V2KeywordController(V2KeywordService keywordService) {
        this.keywordService = keywordService;
    }


    @GetMapping("/hottest-keywords")
    public List<V2HottestKeyword> getTopFiveHottestKeywords() {
        return keywordService.getTopFiveHottestKeywords();
    }
}
