package com.example.facade;

import com.example.exception.PasswordValidationException;

public class PasswordValidatorFacade {

    public boolean isValid(String password) {
        if (null != password && password.length() > 0) {
            return true;
        } else {
            throw new PasswordValidationException("Password validation failed as it does not meet password validation rules");
        }
    }
}