package com.cbelhaffef.dajt.api.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class RoleListResponse {

    @ApiModelProperty(required = true, value = "")
    private List<Role> items;

}
