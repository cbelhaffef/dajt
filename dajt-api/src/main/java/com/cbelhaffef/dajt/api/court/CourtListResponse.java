package com.cbelhaffef.dajt.api.court;

import com.cbelhaffef.dajt.api.PageResponse;
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
