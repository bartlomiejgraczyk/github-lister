package pl.bgraczyk.githublister.dto;

import com.google.common.base.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LanguageStatsDTO implements Comparable<LanguageStatsDTO> {

    String language;
    Long totalBytes;

    @Override
    public int compareTo(LanguageStatsDTO o) {
        return this.getTotalBytes().compareTo(o.getTotalBytes());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        LanguageStatsDTO that = (LanguageStatsDTO) o;
        return Objects.equal(getLanguage(), that.getLanguage());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getLanguage());
    }
}
