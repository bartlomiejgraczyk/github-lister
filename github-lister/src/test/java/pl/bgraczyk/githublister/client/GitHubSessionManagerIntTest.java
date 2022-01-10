package pl.bgraczyk.githublister.client;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kohsuke.github.GitHub;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.bgraczyk.githublister.config.GitHubProperties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GitHubSessionManagerIntTest {

    @Mock
    private GitHubProperties gitHubProperties;

    @InjectMocks
    private GitHubSessionManager gitHubSessionManager;

    @Test
    void should_connect_anonymously_when_token_is_invalid() throws IOException {
        String invalidToken = "invalid-access-token";
        when(gitHubProperties.getAccessToken()).thenReturn(invalidToken);

        GitHub anonymousGitHubClient = gitHubSessionManager.create();

        assertThat(anonymousGitHubClient)
            .extracting(GitHub::isAnonymous)
            .isEqualTo(true);
    }
}
