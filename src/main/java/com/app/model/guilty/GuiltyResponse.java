package com.app.model.guilty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper=false)
public class GuiltyResponse {
    @ApiModelProperty(required = true, value = "")
    private List<Guilty> items;
}
