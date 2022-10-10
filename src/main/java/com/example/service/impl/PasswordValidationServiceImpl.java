package com.example.service.impl;

import com.example.exception.PasswordValidationException;
import com.example.model.PasswordValidationRule;
import com.example.service.PasswordValidationService;

import java.util.List;
import java.util.stream.Stream;

public class PasswordValidationServiceImpl implements PasswordValidationService {
    @Override
    public boolean isValid(String password,
                           int numberOfRulesApplied,
                           List<Integer> mandatoryRuleId,
                           List<PasswordValidationException> passwordValidationExceptions) {

        PasswordValidationRule[] allPasswordRules = PasswordValidationRule.values();
        if (numberOfRulesApplied == 0) {
            return true;
        }
        //Calculate and consider both numberOfRulesApplied and mandatoryRuleId
        if (mandatoryRuleId != null &&
                !mandatoryRuleId.isEmpty() &&
                numberOfRulesApplied > mandatoryRuleId.size()) {
            numberOfRulesApplied = (numberOfRulesApplied - mandatoryRuleId.size());
            return applyValidationWithNoOfRulesSpecifiedAndMandatoryRules(password, numberOfRulesApplied, mandatoryRuleId, allPasswordRules, passwordValidationExceptions);
        }
        // Consider only mandatoryRuleId
        else if (mandatoryRuleId != null && !mandatoryRuleId.isEmpty()) {
            return applyValidationWithOnlyMandatoryRules(password, mandatoryRuleId, allPasswordRules, passwordValidationExceptions);
        }
        //consider only numberOfRulesApplied
        else {
            return applyValidationWithOnlyNoOfRulesSpecified(password, numberOfRulesApplied, allPasswordRules, passwordValidationExceptions);
        }
    }

    private static boolean applyValidationWithOnlyNoOfRulesSpecified(String password,
                                                                     int numberOfRulesApplied,
                                                                     PasswordValidationRule[] allPasswordRules,
                                                                     List<PasswordValidationException> passwordValidationExceptions) {
        long passwordValidatedCount = Stream.of(allPasswordRules).
                filter(passwordValidationRule -> passwordValidationRule.isValidPassword(password, passwordValidationExceptions)).
                count();
        return (passwordValidatedCount >= numberOfRulesApplied);
    }

    private static boolean applyValidationWithOnlyMandatoryRules(String password,
                                                                 List<Integer> mandatoryRuleId,
                                                                 PasswordValidationRule[] allPasswordRules,
                                                                 List<PasswordValidationException> passwordValidationExceptions) {
        long passwordValidatedCount = mandatoryRuleId.stream().
                filter(ruleId -> Stream.of(allPasswordRules).anyMatch(rule -> (ruleId == rule.getId()) && rule.isValidPassword(password, passwordValidationExceptions))).
                count();
        return (passwordValidatedCount >= mandatoryRuleId.size());
    }

    private static boolean applyValidationWithNoOfRulesSpecifiedAndMandatoryRules(String password,
                                                                                  int numberOfRulesApplied,
                                                                                  List<Integer> mandatoryRuleId,
                                                                                  PasswordValidationRule[] allPasswordRules,
                                                                                  List<PasswordValidationException> passwordValidationExceptions) {
        long mandatoryPasswordValidatedCount = mandatoryRuleId.stream().
                filter(ruleId -> Stream.of(allPasswordRules).anyMatch(rule -> (ruleId == rule.getId()) && rule.isValidPassword(password, passwordValidationExceptions))).
                count();
        long passwordValidatedCount = Stream.of(allPasswordRules).
                filter(passwordValidationRule -> !mandatoryRuleId.contains(passwordValidationRule.getId()) && passwordValidationRule.isValidPassword(password, passwordValidationExceptions)).
                count();
        return (mandatoryPasswordValidatedCount >= mandatoryRuleId.size() && passwordValidatedCount >= numberOfRulesApplied);
    }
}