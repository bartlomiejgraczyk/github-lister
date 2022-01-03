package pl.bgraczyk.githublister.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RepositoryDTO {

    private String name;
    private String stars;
}
