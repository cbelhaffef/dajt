package com.app.model.judgement;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class JudgementResponse {
    @ApiModelProperty(required = true, value = "")
    private List<Judgement> items;
}
