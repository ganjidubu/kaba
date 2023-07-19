package home.dubu.kaba.controller;

import home.dubu.kaba.dto.response.PlaceSearchResponse;
import home.dubu.kaba.dto.response.SearchListResponse;
import home.dubu.kaba.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/places/search")
public class SearchController {
    private final SearchService searchService;


    @GetMapping
    public PlaceSearchResponse searchPlace(@RequestParam String keyword) {
        return searchService.searchByKeyword(keyword);
    }


    @GetMapping("/list")
    public SearchListResponse getSearchList() {
        return searchService.findSearchList();
    }
}
