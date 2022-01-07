package pl.bgraczyk.githublister.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StarStatsDTO {

    @JsonProperty(value = "stars-count")
    private int starsCount;
}
