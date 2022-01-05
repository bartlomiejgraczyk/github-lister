package pl.bgraczyk.githublister.client.pageable.enums;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@ApiModel
public enum SortType {

    ALL("all"),
    OWNER("owner"),
    MEMBER("member");

    private final String text;

    public static String valuesAsString() {
        return Stream.of(values())
            .map(SortType::toString)
            .collect(Collectors.joining(", "));
    }
}
