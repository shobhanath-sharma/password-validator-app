package com.example.service;

import java.util.List;

public interface PasswordValidationService {
    boolean isValid(String password, int numberOfPasswordRules, List<Integer> numberOfMandatoryRuleId);
}