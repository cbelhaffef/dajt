package com.cbelhaffef.dajt.models.role;

import com.cbelhaffef.dajt.entities.Role;
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
