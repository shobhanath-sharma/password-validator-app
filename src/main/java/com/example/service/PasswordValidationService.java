package com.example.service;

import com.example.exception.PasswordValidationException;

import java.util.List;

public interface PasswordValidationService {
    boolean isValid(String password,
                    int numberOfPasswordRules,
                    List<Integer> numberOfMandatoryRuleId,
                    List<PasswordValidationException> passwordValidationExceptions);
}