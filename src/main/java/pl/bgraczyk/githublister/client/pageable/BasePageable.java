package pl.bgraczyk.githublister.client.pageable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BasePageable {

    @ApiModelProperty(value = "Results page you want to retrieve (1..N) (default: 1)", dataType = "integer")
    private int page;

    @ApiModelProperty(value = "Number of records per page (0..100) (default: 30)", dataType = "integer")
    private int size;

    public String toQueryString() {
        return "?page=" + this.getPage() + "&per_page=" + this.getSize();
    }
}
