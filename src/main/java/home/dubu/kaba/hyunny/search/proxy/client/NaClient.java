package home.dubu.kaba.hyunny.search.proxy.client;

import home.dubu.kaba.hyunny.search.domain.external.V2NaItems;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "na-client", url = "${api.naver.url}")
public interface NaClient {

    @GetMapping("/local.json?display=5")
    V2NaItems search(
            @RequestHeader("X-Naver-Client-Id") String clientId,
            @RequestHeader("X-Naver-Client-Secret") String clientSecret,
            @RequestParam("query") String keyword
    );
}
