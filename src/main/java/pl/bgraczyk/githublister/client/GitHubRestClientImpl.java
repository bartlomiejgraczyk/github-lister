package pl.bgraczyk.githublister.client;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
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
import pl.bgraczyk.githublister.client.abstraction.BaseRestClient;
import pl.bgraczyk.githublister.client.abstraction.GitHubRestClient;
import pl.bgraczyk.githublister.client.error.GitHubResponseErrorHandler;
import pl.bgraczyk.githublister.client.error.exception.GitHubResponseException;
import pl.bgraczyk.githublister.client.pageable.RepoPageable;
import pl.bgraczyk.githublister.config.GitHubProperties;
import pl.bgraczyk.githublister.dto.RepositoryDTO;

import static java.util.Objects.isNull;

@Slf4j
@Service
public class GitHubRestClientImpl extends BaseRestClient implements GitHubRestClient {

    private final GitHubProperties gitHubProperties;

    @Autowired
    public GitHubRestClientImpl(GitHubProperties gitHubProperties,
                                RestTemplate restTemplate) {
        super(restTemplate);
        this.gitHubProperties = gitHubProperties;
        errorHandler(new GitHubResponseErrorHandler());
        defaultUri(gitHubProperties.getBaseApiUrl());
    }

    @Override
    public Set<RepositoryDTO> getReposPageByUsername(String username, RepoPageable pageable) {
        String url = String.format(gitHubProperties.getFullUserReposResourceApiUrl(), username);
        HttpEntity<String> entity = new HttpEntity<>(getHttpHeaders());
        Map<String, ?> uriVariablesMap = buildUriVariables(username, pageable);

        try {
            ResponseEntity<RepositoryDTO[]> response =
                restTemplate.exchange(url, HttpMethod.GET, entity, RepositoryDTO[].class, uriVariablesMap);
            return isNull(response.getBody()) ? Collections.emptySet() : Sets.newHashSet(response.getBody());
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
        httpHeaders.setAccept(Collections.singletonList(MediaType.parseMediaType("application/vnd.github.v3+json")));
        return httpHeaders;
    }

    private Map<String, ?> buildUriVariables(String username, RepoPageable pageable) {
        Map<String, String> uriVariablesMap = new HashMap<>();
        uriVariablesMap.put("username", username);
        uriVariablesMap.put("query", pageable.toQueryString());
        return uriVariablesMap;
    }
}
