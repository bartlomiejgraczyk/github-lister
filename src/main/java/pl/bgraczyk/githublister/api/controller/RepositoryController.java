package pl.bgraczyk.githublister.api.controller;

import java.util.List;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.bgraczyk.githublister.client.pageable.RepoPageable;
import pl.bgraczyk.githublister.dto.RepositoryDTO;
import pl.bgraczyk.githublister.service.abstraction.GitHubIntegrationService;

@RestController
@RequestMapping("/api/v1")
@Api(value = "repositories", tags = "repositories")
public class RepositoryController {

    private final GitHubIntegrationService gitHubIntegrationService;

    @Autowired
    public RepositoryController(GitHubIntegrationService gitHubIntegrationService) {
        this.gitHubIntegrationService = gitHubIntegrationService;
    }

    @GetMapping(value = "/test")
    public String test() {
        return "hello world!";
    }

    @GetMapping(value = "/repositories/{username}")
    public List<RepositoryDTO> getRepositories(@PathVariable(value = "username") String username, RepoPageable pageable) {
        return gitHubIntegrationService.getRepositoriesPageByUsername(username, pageable);
    }
}
