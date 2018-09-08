package com.cbelhaffef.dajt.model.folder;

import com.cbelhaffef.dajt.enums.FolderPriority;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class FolderPriorityResponse {
    @ApiModelProperty(required = true, value = "")
    private List<FolderPriority> items;
}
