package home.dubu.kaba.hyunny.search.controller;

import home.dubu.kaba.hyunny.search.domain.dto.V2Place;
import home.dubu.kaba.hyunny.search.service.V2SearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v2")
public class V2SearchController {

    private final V2SearchService searchService;

    public V2SearchController(V2SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/places")
    public List<V2Place> getPlacesByKeyword(@RequestParam("keyword") String keyword) {
        return searchService.getPlacesByKeyword(keyword);
    }
}
