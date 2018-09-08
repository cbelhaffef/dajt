package com.cbelhaffef.dajt.model.court;

import com.cbelhaffef.dajt.model.response.PageResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper=false)
public class CourtListResponse extends PageResponse {
    @ApiModelProperty(required = true, value = "")
    private List<Court> items;
}
