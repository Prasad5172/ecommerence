package com.prasad.ecommerence.exception;

import org.springframework.stereotype.Component;

public class ProductException extends Exception {
    public ProductException(String message){
        super(message);
    }

}
