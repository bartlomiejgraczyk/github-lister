package pl.bgraczyk.githublister.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kohsuke.github.GHRepository;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.bgraczyk.githublister.dto.RepositoryDTO;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GitHubRepositoryMapperTest {

    @Mock
    private GHRepository ghRepository;

    @Test
    void should_map_not_null_attributes() {
        when(ghRepository.getName()).thenReturn("repository");
        when(ghRepository.getStargazersCount()).thenReturn(10);

        RepositoryDTO mappedRepo = GitHubRepositoryMapper.map(ghRepository);

        assertThat(mappedRepo.getName()).isEqualTo("repository");
        assertThat(mappedRepo.getStars()).isEqualTo(10);
    }

    @Test
    void should_map_when_name_is_null() {
        doReturn(null).when(ghRepository).getName();
        doReturn(0).when(ghRepository).getStargazersCount();

        RepositoryDTO mappedRepo = GitHubRepositoryMapper.map(ghRepository);

        assertThat(mappedRepo.getName()).isNull();
    }

    @Test
    void should_map_when_name_is_empty() {
        when(ghRepository.getName()).thenReturn("");
        when(ghRepository.getStargazersCount()).thenReturn(0);

        RepositoryDTO mappedRepo = GitHubRepositoryMapper.map(ghRepository);

        assertThat(mappedRepo.getName()).isEmpty();
    }
}
