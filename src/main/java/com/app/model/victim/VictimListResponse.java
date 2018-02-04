package com.app.model.victim;

import com.app.model.response.PageResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper=false)
public class VictimListResponse  extends PageResponse {
    @ApiModelProperty(required = true, value = "")
    private List<Victim> items;
}
