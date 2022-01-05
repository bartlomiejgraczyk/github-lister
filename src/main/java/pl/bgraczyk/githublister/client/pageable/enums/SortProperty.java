package pl.bgraczyk.githublister.client.pageable.enums;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ApiModel
public enum SortProperty {

    CREATED("created"),
    UPDATED("updated"),
    PUSHED("pushed"),
    FULL_NAME("full_name");

    private final String text;

    public static String valuesAsString() {
        return Stream.of(values())
            .map(SortProperty::toString)
            .collect(Collectors.joining(", "));
    }
}
