package com.app.model.folder;

import com.app.enums.FolderPriority;
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
