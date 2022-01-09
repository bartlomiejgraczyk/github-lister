package pl.bgraczyk.githublister.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.kohsuke.github.GHRepository;
import pl.bgraczyk.githublister.dto.RepositoryDTO;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GitHubRepositoryMapper {

    public static RepositoryDTO map(GHRepository repository) {
        RepositoryDTO repo = new RepositoryDTO();
        repo.setName(repository.getName());
        repo.setStars(repository.getStargazersCount());
        return repo;
    }
}
