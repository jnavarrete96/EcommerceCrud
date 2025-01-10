package com.example.ecommerce.exception;

public class GeneralException extends RuntimeException{
    public GeneralException (String message,Throwable cause){
        super(message,cause);
    }
}
