package home.dubu.kaba.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "naver")
public class NaverProperties {
    private String clientId;
    private String secret;
    private String url;
    
}
