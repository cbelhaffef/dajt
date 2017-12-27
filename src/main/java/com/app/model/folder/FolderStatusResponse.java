package com.app.model.folder;

import com.app.enums.FolderStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class FolderStatusResponse {
    @ApiModelProperty(required = true, value = "")
    private List<FolderStatus> items;
}
