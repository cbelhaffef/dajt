package com.cbelhaffef.dajt.api.folder;

import com.cbelhaffef.dajt.api.status.StatusFolder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class FolderStatusResponse {
    @ApiModelProperty(required = true, value = "")
    private List<StatusFolder> items;
}
