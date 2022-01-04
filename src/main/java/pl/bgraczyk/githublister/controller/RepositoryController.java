package pl.bgraczyk.githublister.controller;

import java.util.List;
import java.util.Set;
import javax.websocket.server.PathParam;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.bgraczyk.githublister.client.abstraction.GitHubRestClient;
import pl.bgraczyk.githublister.dto.RepositoryDTO;

@RestController
@RequestMapping("/api/v1")
@Api(value = "repositories", tags = "repositories")
public class RepositoryController {

    private final GitHubRestClient gitHubRestClient;

    @Autowired
    public RepositoryController(GitHubRestClient gitHubRestClient) {
        this.gitHubRestClient = gitHubRestClient;
    }

    @GetMapping(value = "/test")
    public String test() {
        return "hello world!";
    }

    @GetMapping(value = "/repositories/{username}")
    public Set<RepositoryDTO> getRepositories(@PathParam(value = "username") String username) {
        return gitHubRestClient.getReposPageByUsername(username, 0, 1000);
    }
}
