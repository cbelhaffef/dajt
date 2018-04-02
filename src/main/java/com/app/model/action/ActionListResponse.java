package com.app.model.action;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ActionListResponse {

    @ApiModelProperty(required = true, value = "")
    private List<Action> items;

}
