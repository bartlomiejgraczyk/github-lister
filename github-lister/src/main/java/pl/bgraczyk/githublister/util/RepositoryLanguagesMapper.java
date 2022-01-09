package pl.bgraczyk.githublister.util;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GHRepository;
import pl.bgraczyk.githublister.dto.LanguageStatsDTO;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class RepositoryLanguagesMapper {

    public static Map<String, Long> map(GHRepository repository) {
        try {
            return repository.listLanguages();
        } catch (IOException e) {
            log.warn("Error during mapping repository {} languages, reason: {}", repository.getFullName(), e.getLocalizedMessage());
            return Collections.emptyMap();
        }
    }

    public static List<LanguageStatsDTO> aggregate(Map<String, Long> languages) {
        return languages.entrySet()
            .stream()
            .map(language -> new LanguageStatsDTO(language.getKey(), language.getValue()))
            .sorted(Comparator.reverseOrder())
            .collect(Collectors.toList());
    }
}
