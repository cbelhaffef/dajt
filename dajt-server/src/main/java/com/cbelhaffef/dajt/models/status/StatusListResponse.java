package com.cbelhaffef.dajt.model.status;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class StatusListResponse {

    @ApiModelProperty(required = true, value = "")
    private List<Status> items;

}
