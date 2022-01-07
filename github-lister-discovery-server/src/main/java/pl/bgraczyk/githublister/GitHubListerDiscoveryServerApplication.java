package pl.bgraczyk.githublister;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class GitHubListerDiscoveryServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GitHubListerDiscoveryServerApplication.class, args);
    }
}
