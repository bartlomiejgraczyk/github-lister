package pl.bgraczyk.githublister.client;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import pl.bgraczyk.githublister.client.abstraction.GitHubHttpClient;
import pl.bgraczyk.githublister.client.abstraction.SessionManager;
import pl.bgraczyk.githublister.client.error.exception.GitHubResponseClientException;
import pl.bgraczyk.githublister.util.RepositoryLanguagesMapper;

@Lazy
@Slf4j
@Service
public class GitHubHttpClientImpl implements GitHubHttpClient {

    private final GitHub gitHubClient;

    @Autowired
    public GitHubHttpClientImpl(SessionManager<GitHub> gitHubSessionManager) {
        this.gitHubClient = gitHubSessionManager.create();
    }

    public Set<GHRepository> getReposByUsername(String username) {
        try {
            return gitHubClient.getUser(username)
                .listRepositories()
                .toSet();
        } catch (GitHubResponseClientException e) {
            String errorMsg = String.format("Error during retrieving repositories for user %s, reason: %s, status code: %s", username, e.getLocalizedMessage(), e.getCode());
            log.warn(errorMsg);
            throw new GitHubResponseClientException(errorMsg);
        } catch (IOException e) {
            String errorMsg = String.format("Error during retrieving repositories for user %s, reason: %s", username, e.getLocalizedMessage());
            log.warn(errorMsg);
            throw new GitHubResponseClientException(errorMsg);
        }
    }

    @Override
    public List<Map<String, Long>> getUserReposLanguages(String username) {
        try {
            return gitHubClient.getUser(username)
                .listRepositories()
                .toSet()
                .stream()
                .map(RepositoryLanguagesMapper::map)
                .collect(Collectors.toList());
        } catch (GitHubResponseClientException e) {
            String errorMsg = String.format("Error during retrieving repositories languages for user %s, reason: %s, status code: %s", username, e.getLocalizedMessage(), e.getCode());
            log.warn(errorMsg);
            throw new GitHubResponseClientException(errorMsg);
        } catch (IOException e) {
            String errorMsg = String.format("Error during retrieving repositories languages for user %s, reason: %s", username, e.getLocalizedMessage());
            log.warn(errorMsg);
            throw new GitHubResponseClientException(errorMsg);
        }
    }
}
