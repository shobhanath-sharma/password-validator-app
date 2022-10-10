package com.example.facade;

import com.example.exception.PasswordValidationException;
import com.example.service.PasswordValidationService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PasswordValidatorFacade {

    private final PasswordValidationService passwordValidationService;
    private final int numberOfRulesApplied;
    private List<Integer> mandatoryRuleIdList;

    public PasswordValidatorFacade(final PasswordValidationService passwordValidationService,
                                   final int numberOfRulesApplied,
                                   final List<Integer> mandatoryRuleIdList) {
        this.passwordValidationService = passwordValidationService;
        this.numberOfRulesApplied = numberOfRulesApplied;
        this.mandatoryRuleIdList = mandatoryRuleIdList;
    }

    public PasswordValidatorFacade(final PasswordValidationService passwordValidationService) {
        this.passwordValidationService = passwordValidationService;
        this.numberOfRulesApplied = 3;
        this.mandatoryRuleIdList = Collections.singletonList(4);
        ;
    }

    public boolean isValid(String password) {
        List<PasswordValidationException> passwordValidationExceptions = new ArrayList<>();
        boolean result = passwordValidationService.isValid(password, numberOfRulesApplied, mandatoryRuleIdList, passwordValidationExceptions);
        if (!result && !passwordValidationExceptions.isEmpty()) {
            throw passwordValidationExceptions.get(0);
        }
        return result;
    }
}