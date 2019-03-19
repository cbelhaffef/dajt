package com.cbelhaffef.dajt.api.auth;

import com.cbelhaffef.dajt.security.service.UserPrinciple;
import lombok.Data;

@Data
public class SessionData {
    private String  token;
    private final static String TOKEN_TYPE = "Bearer";
    private UserPrinciple userPrinciple;

    public SessionData(String token, UserPrinciple userPrinciple) {
        this.token = TOKEN_TYPE + " " + token;
        this.userPrinciple = userPrinciple;
    }

}
