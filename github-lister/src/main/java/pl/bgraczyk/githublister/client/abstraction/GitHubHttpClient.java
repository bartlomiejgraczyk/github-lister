package pl.bgraczyk.githublister.client.abstraction;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.kohsuke.github.GHRepository;

public interface GitHubHttpClient {

    Set<GHRepository> getReposByUsername(String username);

    List<Map<String, Long>> getUserReposLanguages(String username);
}
