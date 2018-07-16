package com.cbelhaffef.dajt.model.session;

import lombok.*;
import java.util.*;
import io.swagger.annotations.ApiModelProperty;

@Data
public class SessionItem {
    private String  token;
    private String  username;
    private Long    userId;
    private String  firstname;
    private String  lastname;
    private String  email;
    private List<String> roles;
}
