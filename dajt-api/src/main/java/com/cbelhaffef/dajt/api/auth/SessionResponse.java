package com.cbelhaffef.dajt.api.auth;

import io.swagger.annotations.*;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper=false)
public class SessionResponse extends OperationResponse  {

  @ApiModelProperty(required = true)
  private SessionData item;

  public SessionResponse(String message, ResponseStatusEnum status) {
    this.setOperationMessage(message);
    this.setOperationStatus(status);
  }

  public SessionResponse(String message, SessionData item, ResponseStatusEnum status) {
    this.setOperationMessage(message);
    this.setItem(item);
    this.setOperationStatus(status);
  }

}
