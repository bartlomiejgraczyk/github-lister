package pl.bgraczyk.githublister.client;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import pl.bgraczyk.githublister.client.abstraction.SessionManager;
import pl.bgraczyk.githublister.client.error.GitHubClientRateLimitHandler;
import pl.bgraczyk.githublister.client.error.exception.GitHubResponseClientException;
import pl.bgraczyk.githublister.config.GitHubProperties;

@Lazy
@Component
@Slf4j
public class GitHubSessionManager implements SessionManager<GitHub> {

    private final GitHubProperties gitHubProperties;

    @Autowired
    public GitHubSessionManager(GitHubProperties gitHubProperties) {
        this.gitHubProperties = gitHubProperties;
    }

    @Override
    public GitHub create() {
        try {
            GitHub gitHub = new GitHubBuilder()
                .withOAuthToken(gitHubProperties.getAccessToken())
                .withRateLimitHandler(new GitHubClientRateLimitHandler())
                .build();

            if (!gitHub.isCredentialValid()) {
                log.info("Given GitHub credentials are invalid. Connecting anonymously...");
                return connectAnonymously();
            }

            return gitHub;
        } catch (IOException e) {
            log.warn("Error during retrieving GitHub connection, reason: {}", e.getLocalizedMessage());
            return connectAnonymously();
        }
    }

    private GitHub connectAnonymously() {
        try {
            return new GitHubBuilder()
                .withRateLimitHandler(new GitHubClientRateLimitHandler())
                .build();
        } catch (IOException e) {
            String errorMsg = String.format("Error during retrieving GitHub anonymous connection, reason: %s", e.getLocalizedMessage());
            log.error(errorMsg);
            throw new GitHubResponseClientException(errorMsg);
        }
    }
}
