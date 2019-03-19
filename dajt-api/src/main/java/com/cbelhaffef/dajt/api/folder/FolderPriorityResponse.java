package com.cbelhaffef.dajt.api.folder;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class FolderPriorityResponse {
    @ApiModelProperty(required = true, value = "")
    private List<FolderPriorityEnum> items;
}
