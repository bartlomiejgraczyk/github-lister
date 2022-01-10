package pl.bgraczyk.githublister.service;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.bgraczyk.githublister.client.abstraction.GitHubHttpClient;
import pl.bgraczyk.githublister.dto.LanguageStatsDTO;
import pl.bgraczyk.githublister.dto.RepositoryDTO;
import pl.bgraczyk.githublister.dto.StarStatsDTO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GitHubIntegrationServiceImplTest {

    @Mock
    private GitHubHttpClient gitHubClient;

    @InjectMocks
    private GitHubIntegrationServiceImpl gitHubIntegrationService;

    @Test
    void should_return_empty_repositories_list_when_no_repositories_were_found() {
        when(gitHubClient.getReposByUsername(anyString())).thenReturn(Collections.emptySet());

        List<RepositoryDTO> repositories = gitHubIntegrationService.getUserRepositoriesSorted(anyString());

        assertThat(repositories)
            .isNotNull()
            .isEmpty();
    }

    @Test
    void should_return_zero_stars_when_no_repositories_were_found() {
        when(gitHubClient.getReposByUsername(anyString())).thenReturn(Collections.emptySet());

        StarStatsDTO stars = gitHubIntegrationService.getUserRepositoriesStarsCount(anyString());

        assertThat(stars)
            .isNotNull()
            .extracting(StarStatsDTO::getStarsCount)
            .isEqualTo(0);
    }

    @Test
    void should_return_empty_languages_list_when_no_repositories_were_found() {
        when(gitHubClient.getUserReposLanguages(anyString())).thenReturn(Collections.emptyList());

        List<LanguageStatsDTO> repositoriesLanguages = gitHubIntegrationService.getUserMostlyUsedLanguages(anyString(), 10);

        assertThat(repositoriesLanguages)
            .isNotNull()
            .isEmpty();
    }
}
