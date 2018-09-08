package com.cbelhaffef.dajt.models.actionlog;

import com.cbelhaffef.dajt.entities.ActionLog;
import com.cbelhaffef.dajt.models.response.PageResponse;
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
