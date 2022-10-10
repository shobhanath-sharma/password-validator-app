package com.example.service.impl;

import com.example.service.PasswordValidationService;

import java.util.List;

public class PasswordValidationServiceImpl implements PasswordValidationService {
    @Override
    public boolean isValid(String password, int numberOfRulesApplied, List<Integer> mandatoryRuleId) {
        return true;
    }
}