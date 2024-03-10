package com.github.GuilhermeBauer.Ecommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidJwtAuthenticationException extends AuthenticationException {

    public InvalidJwtAuthenticationException(String ex){
        super(ex);
    }
    public InvalidJwtAuthenticationException() {
        super("It is not allowed persisted a null object!");
    }
}
