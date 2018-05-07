package com.cbelhaffef.dajt.model.user;

import com.cbelhaffef.dajt.model.response.OperationResponse;
import io.swagger.annotations.*;
import lombok.*;
import java.util.*;
import com.cbelhaffef.dajt.model.response.*;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserResponse extends OperationResponse {
    private User data = new User();
}
