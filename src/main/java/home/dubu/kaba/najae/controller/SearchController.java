package home.dubu.kaba.najae.controller;

import home.dubu.kaba.najae.dto.response.PlaceSearchResponse;
import home.dubu.kaba.najae.dto.response.SearchListResponse;
import home.dubu.kaba.najae.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/places/search")
public class SearchController {
    private final SearchService searchService;


    @GetMapping
    public PlaceSearchResponse searchPlace(@RequestParam @NotBlank String keyword) {
        return searchService.searchByKeyword(keyword);
    }


    @GetMapping("/list")
    public SearchListResponse getSearchList() {
        return searchService.findSearchList();
    }
}
