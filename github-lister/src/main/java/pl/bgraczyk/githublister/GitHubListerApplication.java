package pl.bgraczyk.githublister;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class GitHubListerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GitHubListerApplication.class, args);
    }
}
