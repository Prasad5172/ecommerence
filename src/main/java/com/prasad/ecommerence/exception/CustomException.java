package com.prasad.ecommerence.exception;

import org.springframework.stereotype.Component;

public class CustomException extends RuntimeException{
    public CustomException(String message){
        super(message);
    }
}
