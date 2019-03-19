package com.cbelhaffef.dajt.api.user;

import io.swagger.annotations.*;
import lombok.*;

@Data
public class LoginForm {

  @ApiModelProperty(example = "demo", required = true)
  private String username = "";

  @ApiModelProperty(example = "demo", required = true)
  private String password = "";

  public LoginForm() {
  }

  public LoginForm(String username, String password) {
    this.username = username;
    this.password = password;
  }
}
