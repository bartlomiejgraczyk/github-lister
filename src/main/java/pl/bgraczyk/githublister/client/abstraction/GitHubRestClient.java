package pl.bgraczyk.githublister.client.abstraction;

import java.util.Set;
import pl.bgraczyk.githublister.client.pageable.RepoPageable;
import pl.bgraczyk.githublister.dto.RepositoryDTO;

public interface GitHubRestClient {

    Set<RepositoryDTO> getReposPageByUsername(String username, RepoPageable pageable);
}
