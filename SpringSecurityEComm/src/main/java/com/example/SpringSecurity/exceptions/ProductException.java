package com.example.SpringSecurity.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ProductException extends RuntimeException{
    public ProductException(String message){
        super(message);
    }
}
