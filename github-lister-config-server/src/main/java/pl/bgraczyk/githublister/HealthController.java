package pl.bgraczyk.githublister;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class HealthController {

    @Value("${spring.cloud.config.server.git.username}")
    private String username;

    @Value("${spring.cloud.config.server.git.password}")
    private String sshKey;

    @Value("${spring.application.name}")
    private String applicationName;

    @GetMapping(value = "username")
    public String getUsername() {
        return username;
    }

    @GetMapping(value = "ssh")
    public String getSsh() {
        return sshKey;
    }

    @GetMapping(value = "application")
    public String getApplicationName() {
        return applicationName;
    }
}
