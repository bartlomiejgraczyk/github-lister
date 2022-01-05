package pl.bgraczyk.githublister.service.abstraction;

import java.util.List;
import pl.bgraczyk.githublister.client.pageable.RepoPageable;
import pl.bgraczyk.githublister.dto.RepositoryDTO;

public interface GitHubIntegrationService {

    List<RepositoryDTO> getRepositoriesPageByUsername(String username, RepoPageable pageable);
}
