package com.cbelhaffef.dajt.model.priority;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class PriorityListResponse {

    @ApiModelProperty(required = true, value = "")
    private List<Priority> items;

}
