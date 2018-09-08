package com.cbelhaffef.dajt.models.action;

import com.cbelhaffef.dajt.entities.Action;
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
