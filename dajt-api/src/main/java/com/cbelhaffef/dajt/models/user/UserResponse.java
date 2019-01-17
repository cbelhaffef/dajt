package com.cbelhaffef.dajt.models.user;

import com.cbelhaffef.dajt.dao.entities.User;
import com.cbelhaffef.dajt.models.response.OperationResponse;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserResponse extends OperationResponse {
    private User data = new User();
}
