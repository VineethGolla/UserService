package com.scaler.userserviceauth.exceptions;

public class PasswordMismatchException extends Exception{
    //checked exception - compiler checks, must be handled.
    public PasswordMismatchException(String message) {
        super(message);
    }
}
