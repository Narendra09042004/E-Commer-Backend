package com.dmart.ecommerce.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ResourceIncorrectException extends RuntimeException
{
    public ResourceIncorrectException(String message)
    {
        super(message);
    }
}
