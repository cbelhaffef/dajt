package com.app.model.victim;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper=false)
public class VictimResponse {
    @ApiModelProperty(required = true, value = "")
    private List<Victim> items;
}
