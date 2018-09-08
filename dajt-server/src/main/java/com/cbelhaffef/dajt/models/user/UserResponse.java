package com.cbelhaffef.dajt.model.user;

import com.cbelhaffef.dajt.model.response.OperationResponse;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserResponse extends OperationResponse {
    private User data = new User();
}
