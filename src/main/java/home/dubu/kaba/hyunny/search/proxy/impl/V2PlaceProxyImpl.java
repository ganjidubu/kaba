package home.dubu.kaba.hyunny.search.proxy.impl;

import home.dubu.kaba.hyunny.search.config.V2ApiProperties;
import home.dubu.kaba.hyunny.search.domain.dto.V2Place;
import home.dubu.kaba.hyunny.search.proxy.V2PlaceProxy;
import home.dubu.kaba.hyunny.search.proxy.client.KaClient;
import home.dubu.kaba.hyunny.search.proxy.client.NaClient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class V2PlaceProxyImpl implements V2PlaceProxy {

    private final V2ApiProperties apiProperties;
    private final KaClient kaClient;
    private final NaClient naClient;

    public V2PlaceProxyImpl(
            V2ApiProperties apiProperties,
            KaClient kaClient,
            NaClient naClient
    ) {
        this.apiProperties = apiProperties;
        this.kaClient = kaClient;
        this.naClient = naClient;
    }

    @Override
    public List<V2Place> getPlacesInKaByKeyword(String keyword) {
        var authorizationToken = String.format("KakaoAK %s", apiProperties.getKaToken());
        return kaClient.search(authorizationToken, keyword)
                .getDocuments()
                .stream()
                .map(V2Place::of)
                .collect(Collectors.toList());
    }

    @Override
    public List<V2Place> getPlacesInNaByKeyword(String keyword) {
        var id = apiProperties.getNaClientId();
        var secret = apiProperties.getNaClientSecret();
        return naClient.search(id, secret, "짜장")
                .getItems()
                .stream()
                .map(V2Place::of)
                .collect(Collectors.toList());
    }
}
