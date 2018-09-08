//This is a common http response model for providing data series

package com.cbelhaffef.dajt.model.response;

import lombok.*;
import java.util.*;

import com.cbelhaffef.dajt.model.data.*;

@Data
@EqualsAndHashCode(callSuper=false)
public class SingleDataSeriseResponse extends OperationResponse {
    private List<SingleSerise> items;
}
