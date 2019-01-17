package com.cbelhaffef.dajt.models.folder;

import com.cbelhaffef.dajt.dao.entities.Folder;
import com.cbelhaffef.dajt.models.response.PageResponse;
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
