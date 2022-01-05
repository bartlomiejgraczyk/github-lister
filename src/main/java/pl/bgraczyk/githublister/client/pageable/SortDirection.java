package pl.bgraczyk.githublister.client.pageable;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ApiModel
public enum SortDirection {

    ASC("asc"),
    DESC("desc");

    private final String text;

    public static String valuesAsString() {
        return Stream.of(values())
            .map(SortDirection::toString)
            .collect(Collectors.joining(", "));
    }
}
