package com.prasad.ecommerence.exception;

import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;

public class OrderException extends Exception {
    public OrderException(String message){
        super(message);
    }
}
