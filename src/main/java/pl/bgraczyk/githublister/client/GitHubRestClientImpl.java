package pl.bgraczyk.githublister.client;

import java.util.Collections;
import java.util.Set;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pl.bgraczyk.githublister.client.abstraction.GitHubRestClient;
import pl.bgraczyk.githublister.client.exception.GitHubResponseErrorHandler;
import pl.bgraczyk.githublister.client.exception.GitHubResponseException;
import pl.bgraczyk.githublister.config.GitHubProperties;
import pl.bgraczyk.githublister.dto.RepositoryDTO;

@Slf4j
@Service
public class GitHubRestClientImpl implements GitHubRestClient {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;
    private static final int MAX_SIZE = 1000;

    private final GitHubProperties gitHubProperties;
    private final RestTemplate restTemplate;

    @Autowired
    public GitHubRestClientImpl(GitHubProperties gitHubProperties, RestTemplate restTemplate) {
        this.gitHubProperties = gitHubProperties;
        this.restTemplate = restTemplate;
        this.restTemplate.setErrorHandler(new GitHubResponseErrorHandler());
    }

    @Override
    public Set<RepositoryDTO> getReposPageByUsername(String username, int page, int size) {
        String url = String.format(gitHubProperties.getFullUserReposResourceApiUrl(), username);
        HttpEntity<String> entity = new HttpEntity<>(getHttpHeaders());

        try {
            ResponseEntity<RepositoryDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity, RepositoryDTO.class);
            return Sets.newHashSet(response.getBody());
        } catch (GitHubResponseException e) {
            String errorMsg = String.format("Error during retrieving repositories for user %s, reason: %s, status code: %s", username, e.getLocalizedMessage(), e.getCode());
            log.warn(errorMsg);
            throw new GitHubResponseException(errorMsg);
        } catch (RestClientException e) {
            String errorMsg = String.format("Error during retrieving repositories for user %s, reason: %s", username, e.getLocalizedMessage());
            log.warn(errorMsg);
            throw new GitHubResponseException(errorMsg);
        }
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return httpHeaders;
    }
}
