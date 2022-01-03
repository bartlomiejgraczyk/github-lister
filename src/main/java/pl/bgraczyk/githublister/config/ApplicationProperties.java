package pl.bgraczyk.githublister.config;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Lazy(value = false)
@Getter
@Setter
@NoArgsConstructor
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app")
public class ApplicationProperties {

    private final Client client = new Client();

    @Data
    @NoArgsConstructor
    public static class Client {
        private Integer readTimeoutSeconds;
    }
}
