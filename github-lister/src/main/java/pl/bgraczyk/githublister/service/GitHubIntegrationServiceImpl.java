package pl.bgraczyk.githublister.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GHRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bgraczyk.githublister.client.abstraction.GitHubHttpClient;
import pl.bgraczyk.githublister.dto.LanguageStatsDTO;
import pl.bgraczyk.githublister.dto.RepositoryDTO;
import pl.bgraczyk.githublister.dto.StarStatsDTO;
import pl.bgraczyk.githublister.service.abstraction.GitHubIntegrationService;
import pl.bgraczyk.githublister.util.GitHubRepositoryMapper;
import pl.bgraczyk.githublister.util.RepositoryLanguagesMapper;

@Slf4j
@Service
public class GitHubIntegrationServiceImpl implements GitHubIntegrationService {

    private final GitHubHttpClient gitHubClient;

    @Autowired
    public GitHubIntegrationServiceImpl(GitHubHttpClient gitHubHttpClient) {
        this.gitHubClient = gitHubHttpClient;
    }

    @Override
    public List<RepositoryDTO> getUserRepositoriesSorted(String username) {
        return gitHubClient.getReposByUsername(username)
            .stream()
            .map(GitHubRepositoryMapper::map)
            .sorted(Comparator.reverseOrder())
            .collect(Collectors.toList());
    }

    @Override
    public StarStatsDTO getUserRepositoriesStarsCount(String username) {
        int starsCount = gitHubClient.getReposByUsername(username)
            .stream()
            .mapToInt(GHRepository::getStargazersCount)
            .sum();

        return new StarStatsDTO(starsCount);
    }

    @Override
    public List<LanguageStatsDTO> getUserMostlyUsedLanguages(String username, int count) {
        Map<String, Long> languages = gitHubClient.getUserReposLanguages(username)
            .stream()
            .flatMap(map -> map.entrySet().stream())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Long::sum));

        return RepositoryLanguagesMapper.map(languages).subList(0, count);
    }
}
