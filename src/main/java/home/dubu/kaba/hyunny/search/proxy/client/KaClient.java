package home.dubu.kaba.hyunny.search.proxy.client;

import home.dubu.kaba.hyunny.search.domain.external.V2KaDocument;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ka-client", url = "${api.kakao.url}")
public interface KaClient {

    @GetMapping("/search/keyword.json?size=10")
    V2KaDocument search(
            @RequestHeader("Authorization") String apiKey,
            @RequestParam("query") String keyword
    );
}
