package pl.bgraczyk.githublister.data;

import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LanguageData {

    public static Map.Entry<String, Long> getLanguage(String name, Long bytes) {
        return Map.entry(name, bytes);
    }

    public static Map<String, Long> getLanguagesBytesMap() {
        return Map.ofEntries(
            getLanguage("Java", 1000L),
            getLanguage("JavaScript", 750L),
            getLanguage("HTML", 500L),
            getLanguage("CSS", 250L)
        );
    }
}
