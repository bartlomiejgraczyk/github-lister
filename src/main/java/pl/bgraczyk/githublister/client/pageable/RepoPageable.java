package pl.bgraczyk.githublister.client.pageable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.bgraczyk.githublister.client.pageable.enums.SortDirection;
import pl.bgraczyk.githublister.client.pageable.enums.SortProperty;
import pl.bgraczyk.githublister.client.pageable.enums.SortType;

import static java.util.Objects.isNull;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
public class RepoPageable extends BasePageable {

    @ApiModelProperty(value = "The type of relation to the repository. One of: ALL, OWNER, MEMBER (default: OWNER).", allowableValues = "ALL, OWNER, MEMBER")
    private SortType type = SortType.OWNER;

    @ApiModelProperty(value = "The property on which the data will be sorted. One of CREATED, UPDATED, PUSHED, FULL_NAME (default: FULL_NAME).", allowableValues = "CREATED, UPDATED, PUSHED, FULL_NAME")
    private SortProperty property = SortProperty.FULL_NAME;

    @ApiModelProperty(value = "Sorting direction. One of asc or desc (default: ASC).", allowableValues = "ASC, DESC")
    private SortDirection direction = SortDirection.ASC;

    @Override
    public String toQueryString() {
        return super.toQueryString()
            + (isNull(this.getType()) ? "" : "&type=" + this.getType().getText())
            + (isNull(this.getProperty()) ? "" : "&sort=" + this.getProperty().getText())
            + (isNull(this.getDirection()) ? "" : "&direction=" + this.getDirection().getText());
    }
}
