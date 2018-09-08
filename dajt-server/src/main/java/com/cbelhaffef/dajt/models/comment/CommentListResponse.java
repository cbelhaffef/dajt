package com.cbelhaffef.dajt.models.comment;

import com.cbelhaffef.dajt.entities.Comment;
import com.cbelhaffef.dajt.models.response.PageResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper=false)
public class CommentListResponse extends PageResponse {
    @ApiModelProperty(required = true, value = "")
    private List<Comment> items;
}
