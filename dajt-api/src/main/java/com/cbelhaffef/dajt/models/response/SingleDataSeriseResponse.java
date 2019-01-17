//This is a common http response model for providing data series

package com.cbelhaffef.dajt.models.response;

import lombok.*;
import java.util.*;

import com.cbelhaffef.dajt.models.data.*;

@Data
@EqualsAndHashCode(callSuper=false)
public class SingleDataSeriseResponse extends OperationResponse {
    private List<SingleSerise> items;
}
