package pl.bgraczyk.githublister.config;

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
@ConfigurationProperties(prefix = "github")
public class GitHubProperties {

    private String accessToken;
}
