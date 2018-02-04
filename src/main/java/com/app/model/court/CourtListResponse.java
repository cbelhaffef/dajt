package com.app.model.court;

import com.app.model.response.PageResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Pageable;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper=false)
public class CourtListResponse extends PageResponse {
    @ApiModelProperty(required = true, value = "")
    private List<Court> items;
}
