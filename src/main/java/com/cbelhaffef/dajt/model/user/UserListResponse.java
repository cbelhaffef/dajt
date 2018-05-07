package com.cbelhaffef.dajt.model.user;

import com.cbelhaffef.dajt.model.guilty.Guilty;
import com.cbelhaffef.dajt.model.response.PageResponse;
import com.cbelhaffef.dajt.model.response.PageResponse;
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
