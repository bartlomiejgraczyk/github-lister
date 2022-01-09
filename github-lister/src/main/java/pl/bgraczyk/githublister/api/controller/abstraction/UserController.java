package pl.bgraczyk.githublister.api.controller.abstraction;

import java.util.List;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import pl.bgraczyk.githublister.dto.LanguageStatsDTO;
import pl.bgraczyk.githublister.dto.RepositoryDTO;
import pl.bgraczyk.githublister.dto.StarStatsDTO;

@Api(value = "repositories", tags = "repositories")
public interface UserController {

    @GetMapping(value = "/user/{username}/repositories")
    ResponseEntity<List<RepositoryDTO>> getRepositories(@PathVariable(value = "username") String username);

    @GetMapping(value = "/user/{username}/stars")
    ResponseEntity<StarStatsDTO> getStargazersCount(@PathVariable(value = "username") String username);

    @GetMapping(value = "/user/{username}/languages")
    ResponseEntity<List<LanguageStatsDTO>> getLanguages(@PathVariable(value = "username") String username,
                                                        @RequestParam(value = "count", required = false, defaultValue = "10") int count);
}
