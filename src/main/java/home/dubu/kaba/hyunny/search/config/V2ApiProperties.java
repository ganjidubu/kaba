package home.dubu.kaba.hyunny.search.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Getter
@Setter
class Kakao {
    private String token;
}

@Getter
@Setter
class Naver {
    private String clientId;
    private String clientSecret;
}

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "api")
public class V2ApiProperties {
    private Kakao kakao;
    private Naver naver;

    public String getKaToken() {
        return kakao.getToken();
    }

    public String getNaClientId() {
        return naver.getClientId();
    }

    public String getNaClientSecret() {
        return naver.getClientSecret();
    }
}
