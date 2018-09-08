package com.cbelhaffef.dajt.models.accused;

import com.cbelhaffef.dajt.entities.Accused;
import com.cbelhaffef.dajt.models.response.PageResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper=false)
public class AccusedListResponse extends PageResponse{
    @ApiModelProperty(required = true, value = "")
    private List<Accused> items;
}
