package com.cbelhaffef.dajt.api.user;

import com.cbelhaffef.dajt.api.auth.OperationResponse;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserResponse extends OperationResponse {
    private User data = new User();
}
