package com.app.model.affair;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class AffairResponse {
    @ApiModelProperty(required = true, value = "")
    private List<Affair> items;
}
