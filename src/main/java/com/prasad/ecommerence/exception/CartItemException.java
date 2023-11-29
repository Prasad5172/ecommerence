package com.prasad.ecommerence.exception;

import org.springframework.stereotype.Component;

public class CartItemException extends Exception {
    public CartItemException(String message){
        super(message);
    }
}
