package com.cbelhaffef.dajt.models.folder;

import com.cbelhaffef.dajt.dao.enums.StatusFolder;
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
