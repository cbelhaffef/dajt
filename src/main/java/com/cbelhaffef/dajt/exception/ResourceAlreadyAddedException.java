package com.cbelhaffef.dajt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ResourceAlreadyAddedException extends RuntimeException {

    public ResourceAlreadyAddedException() {}

    public ResourceAlreadyAddedException(String var1) {
        super(var1);
    }

    public ResourceAlreadyAddedException(String var1, Throwable var2) {
        super(var1, var2);
    }

}
