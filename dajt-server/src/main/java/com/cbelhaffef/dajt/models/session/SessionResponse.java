package com.cbelhaffef.dajt.models.session;

import com.cbelhaffef.dajt.entities.SessionItem;
import io.swagger.annotations.*;
import lombok.*;
import com.cbelhaffef.dajt.models.response.*;

@Data
@EqualsAndHashCode(callSuper=false)
public class SessionResponse extends OperationResponse {
  @ApiModelProperty(required = true, value = "")
  private SessionItem item;
}
