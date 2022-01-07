package pl.bgraczyk.githublister.service.abstraction;

import java.util.List;
import pl.bgraczyk.githublister.dto.LanguageStatsDTO;
import pl.bgraczyk.githublister.dto.RepositoryDTO;
import pl.bgraczyk.githublister.dto.StarStatsDTO;

public interface GitHubIntegrationService {

    List<RepositoryDTO> getUserRepositoriesSorted(String username);

    StarStatsDTO getUserRepositoriesStarsCount(String username);

    List<LanguageStatsDTO> getUserMostlyUsedLanguages(String username, int count);
}
