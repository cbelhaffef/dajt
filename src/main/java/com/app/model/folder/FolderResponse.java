package com.app.model.folder;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class FolderResponse {
    @ApiModelProperty(required = true, value = "")
    private List<Folder> items;
}
