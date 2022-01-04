package pl.bgraczyk.githublister.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RepositoryDTO {

    private String name;

    @JsonProperty("stargazers_count")
    private String stars;
}
