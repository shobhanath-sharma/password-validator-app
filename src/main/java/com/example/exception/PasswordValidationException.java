package com.example.exception;

public class PasswordValidationException extends RuntimeException {
    public PasswordValidationException(String exceptionMessage) {
        super(exceptionMessage);
    }
}