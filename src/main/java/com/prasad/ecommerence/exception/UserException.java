package com.prasad.ecommerence.exception;

import org.springframework.stereotype.Component;

public class UserException extends Exception{
    public UserException(String message){
        super(message);
    }
}
