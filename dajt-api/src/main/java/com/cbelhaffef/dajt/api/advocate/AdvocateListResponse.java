package com.cbelhaffef.dajt.api.advocate;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper=false)
public class AdvocateListResponse {
    @ApiModelProperty(required = true, value = "")
    private List<Advocate> items;
}
