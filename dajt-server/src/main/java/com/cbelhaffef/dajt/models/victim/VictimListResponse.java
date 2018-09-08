package com.cbelhaffef.dajt.models.victim;

import com.cbelhaffef.dajt.entities.Victim;
import com.cbelhaffef.dajt.models.response.PageResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper=false)
public class VictimListResponse extends PageResponse {
    @ApiModelProperty(required = true, value = "")
    private List<Victim> items;
}
