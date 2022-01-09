package pl.bgraczyk.githublister.api.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.bgraczyk.githublister.api.controller.abstraction.UserController;
import pl.bgraczyk.githublister.dto.LanguageStatsDTO;
import pl.bgraczyk.githublister.dto.RepositoryDTO;
import pl.bgraczyk.githublister.dto.StarStatsDTO;
import pl.bgraczyk.githublister.service.abstraction.GitHubIntegrationService;

@RestController
@RequestMapping("/api/v1")
public class UserControllerImpl implements UserController {

    private final GitHubIntegrationService gitHubIntegrationService;

    @Autowired
    public UserControllerImpl(GitHubIntegrationService gitHubIntegrationService) {
        this.gitHubIntegrationService = gitHubIntegrationService;
    }

    @Override
    @GetMapping(value = "/users/{username}/repositories")
    public ResponseEntity<List<RepositoryDTO>> getRepositories(@PathVariable(value = "username") String username) {

        return ResponseEntity.ok(gitHubIntegrationService.getUserRepositoriesSorted(username));
    }

    @Override
    @GetMapping(value = "/users/{username}/stars")
    public ResponseEntity<StarStatsDTO> getStargazersCount(@PathVariable(value = "username") String username) {

        return ResponseEntity.ok(gitHubIntegrationService.getUserRepositoriesStarsCount(username));
    }

    @Override
    @GetMapping(value = "/users/{username}/languages")
    public ResponseEntity<List<LanguageStatsDTO>> getLanguages(@PathVariable(value = "username") String username,
                                                               @RequestParam(value = "count", required = false, defaultValue = "10") int count) {

        return ResponseEntity.ok(gitHubIntegrationService.getUserMostlyUsedLanguages(username, count));
    }
}
