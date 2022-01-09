package pl.bgraczyk.githublister.util;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import com.google.common.collect.Ordering;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kohsuke.github.GHRepository;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.bgraczyk.githublister.data.LanguageData;
import pl.bgraczyk.githublister.dto.LanguageStatsDTO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RepositoryLanguagesMapperTest {

    @Mock
    private GHRepository ghRepository;

    @Test
    void should_return_empty_map_when_exception_is_thrown() throws IOException {
        when(ghRepository.listLanguages()).thenThrow(new IOException());

        Map<String, Long> extractedLanguages = RepositoryLanguagesMapper.getLanguages(ghRepository);
        assertThat(extractedLanguages)
            .isNotNull()
            .isEmpty();
    }

    @Test
    void should_return_empty_map_when_repository_is_null() {
        Map<String, Long> extractedLanguages = RepositoryLanguagesMapper.getLanguages(null);
        assertThat(extractedLanguages)
            .isNotNull()
            .isEmpty();
    }

    @Test
    void should_return_empty_map_when_no_languages_for_repository_found() throws IOException {
        when(ghRepository.listLanguages()).thenReturn(Collections.emptyMap());

        Map<String, Long> extractedLanguages = RepositoryLanguagesMapper.getLanguages(ghRepository);
        assertThat(extractedLanguages)
            .isNotNull()
            .isEmpty();
    }

    @Test
    void should_return_languages_map_when_are_found_for_repository() throws IOException {
        when(ghRepository.listLanguages()).thenReturn(LanguageData.getLanguagesBytesMap());

        Map<String, Long> extractedLanguages = RepositoryLanguagesMapper.getLanguages(ghRepository);
        assertThat(extractedLanguages)
            .isNotNull()
            .hasSize(4)
            .isEqualTo(LanguageData.getLanguagesBytesMap());
    }

    @Test
    void should_return_empty_list_when_languages_map_is_null() {
        List<LanguageStatsDTO> mappedLanguages = RepositoryLanguagesMapper.map(null);

        assertThat(mappedLanguages)
            .isNotNull()
            .isEmpty();
    }

    @Test
    void should_return_empty_list_when_languages_map_is_empty() {
        List<LanguageStatsDTO> mappedLanguages = RepositoryLanguagesMapper.map(Collections.emptyMap());

        assertThat(mappedLanguages)
            .isNotNull()
            .isEmpty();
    }

    @Test
    void should_return_non_empty_list_when_languages_map_is_not_empty() {
        Map<String, Long> languagesMap = LanguageData.getLanguagesBytesMap();
        List<LanguageStatsDTO> mappedLanguages = RepositoryLanguagesMapper.map(languagesMap);

        assertThat(mappedLanguages)
            .isNotNull()
            .hasSize(4)
            .extracting(LanguageStatsDTO::getLanguage)
            .containsExactlyInAnyOrderElementsOf(languagesMap.keySet());

        assertTrue(Ordering.natural().reverse().isOrdered(mappedLanguages));
    }

}
