package com.cbelhaffef.dajt.model.office;

import com.cbelhaffef.dajt.model.response.PageResponse;
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

