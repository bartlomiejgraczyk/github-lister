package pl.bgraczyk.githublister.service;

import java.util.List;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bgraczyk.githublister.client.abstraction.GitHubRestClient;
import pl.bgraczyk.githublister.client.pageable.RepoPageable;
import pl.bgraczyk.githublister.dto.RepositoryDTO;
import pl.bgraczyk.githublister.service.abstraction.GitHubIntegrationService;

@Slf4j
@Service
public class GitHubIntegrationServiceImpl implements GitHubIntegrationService {

    private final GitHubRestClient gitHubRestClient;

    @Autowired
    public GitHubIntegrationServiceImpl(GitHubRestClient gitHubRestClient) {
        this.gitHubRestClient = gitHubRestClient;
    }

    @Override
    public List<RepositoryDTO> getRepositoriesPageByUsername(String username, RepoPageable pageable) {
        return Lists.newArrayList(gitHubRestClient.getReposPageByUsername(username, pageable));
    }
}
