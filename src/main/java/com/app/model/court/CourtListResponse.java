package com.app.model.court;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper=false)
public class CourtListResponse {
    @ApiModelProperty(required = true, value = "")
    private List<Court> items;
}
