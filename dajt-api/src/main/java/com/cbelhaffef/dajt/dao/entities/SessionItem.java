package com.cbelhaffef.dajt.dao.entities;

import lombok.Data;

import java.util.List;

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
