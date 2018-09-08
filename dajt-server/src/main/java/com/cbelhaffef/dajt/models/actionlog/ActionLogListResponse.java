package com.cbelhaffef.dajt.model.actionlog;

import com.cbelhaffef.dajt.model.response.PageResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ActionLogListResponse extends PageResponse {
    @ApiModelProperty(required = true, value = "")
    private List<ActionLog> items;
}
