package com.cbelhaffef.dajt.models.office;

import com.cbelhaffef.dajt.entities.Office;
import com.cbelhaffef.dajt.models.response.PageResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper=false)
public class OfficeListResponse extends PageResponse {

    @ApiModelProperty(required = true, value = "")
    private List<Office> items;
}

