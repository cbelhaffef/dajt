package com.cbelhaffef.dajt.models.user;

import com.cbelhaffef.dajt.entities.User;
import com.cbelhaffef.dajt.models.response.PageResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper=false)
public class UserListResponse extends PageResponse {
    @ApiModelProperty(required = true, value = "")
    private List<User> items;
}
