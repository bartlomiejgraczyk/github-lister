package pl.bgraczyk.githublister.client.pageable.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import pl.bgraczyk.githublister.client.pageable.SortDirection;
import pl.bgraczyk.githublister.client.pageable.SortProperty;
import pl.bgraczyk.githublister.client.pageable.SortType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface RepoPageableDefault {

    SortType type() default SortType.OWNER;

    SortProperty property() default SortProperty.FULL_NAME;

    SortDirection direction() default SortDirection.ASC;

    int size() default 30;

    int value() default 30;

    int page() default 1;
}
