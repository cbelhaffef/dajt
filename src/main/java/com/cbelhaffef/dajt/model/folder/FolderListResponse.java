package com.cbelhaffef.dajt.model.folder;

import com.cbelhaffef.dajt.model.response.PageResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class FolderListResponse extends PageResponse {

    @ApiModelProperty(required = true, value = "")
    private List<Folder> items;
}
