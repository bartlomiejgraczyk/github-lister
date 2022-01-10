package pl.bgraczyk.githublister.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StarStatsDTO {

    @JsonProperty(value = "stars-count")
    private int starsCount;
}
