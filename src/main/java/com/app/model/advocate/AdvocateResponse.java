package com.app.model.advocate;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper=false)
public class AdvocateResponse {
    @ApiModelProperty(required = true, value = "")
    private List<Advocate> items;
}
