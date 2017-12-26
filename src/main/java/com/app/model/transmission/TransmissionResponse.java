package com.app.model.transmission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class TransmissionResponse {
    @ApiModelProperty(required = true, value = "")
    private List<Transmission> items;
}
