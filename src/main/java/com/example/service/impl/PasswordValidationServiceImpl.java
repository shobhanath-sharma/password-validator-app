package com.example.service.impl;

import com.example.exception.PasswordValidationException;
import com.example.model.PasswordValidationRule;
import com.example.service.PasswordValidationService;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
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
            return applyValidationOnNumberOfRulesAndMandatoryRules(password, numberOfRulesApplied, mandatoryRuleId, allPasswordRules, passwordValidationExceptions);
        }
        // Consider only mandatoryRuleId
        else if (mandatoryRuleId != null && !mandatoryRuleId.isEmpty()) {
            return applyValidationOnMandatoryRules(password, mandatoryRuleId, allPasswordRules, passwordValidationExceptions);
        }
        //consider only numberOfRulesApplied
        else {
            return applyValidationOnNumbersOfRulesSpecified(password, numberOfRulesApplied, allPasswordRules, passwordValidationExceptions);
        }
    }

    private static boolean applyValidationOnNumbersOfRulesSpecified(String password,
                                                                    int numberOfRulesApplied,
                                                                    PasswordValidationRule[] allPasswordRules,
                                                                    List<PasswordValidationException> passwordValidationExceptions) {
        AtomicInteger trueCounter = new AtomicInteger();
        AtomicInteger falseCounter = new AtomicInteger();
        int stopIfNumberOfFailureReached = (allPasswordRules.length) - (numberOfRulesApplied);
        long passwordValidatedCount = Stream.of(allPasswordRules).
                filter(passwordValidationRule -> {
                    boolean result = (trueCounter.get() <= numberOfRulesApplied) &&
                            (falseCounter.get() <= stopIfNumberOfFailureReached) &&
                            passwordValidationRule.isValidPassword(password, passwordValidationExceptions);
                    if (result) {
                        trueCounter.incrementAndGet();
                    } else {
                        falseCounter.incrementAndGet();
                    }
                    return result;
                }).
                filter(passwordValidationRule -> numberOfRulesApplied == trueCounter.get()).
                count();
        return (passwordValidatedCount >= numberOfRulesApplied);
    }

    private static boolean applyValidationOnMandatoryRules(String password,
                                                           List<Integer> mandatoryRuleId,
                                                           PasswordValidationRule[] allPasswordRules,
                                                           List<PasswordValidationException> passwordValidationExceptions) {
        AtomicInteger trueCounter = new AtomicInteger();
        AtomicInteger falseCounter = new AtomicInteger();
        int stopIfNumberOfFailureReached = (allPasswordRules.length) - (mandatoryRuleId.size());

        long passwordValidatedCount = mandatoryRuleId.stream().
                filter(ruleId -> {
                    boolean result = (trueCounter.get() <= mandatoryRuleId.size()) &&
                            (falseCounter.get() <= stopIfNumberOfFailureReached) &&
                            Stream.of(allPasswordRules).anyMatch(rule -> (ruleId == rule.getId()) && rule.isValidPassword(password, passwordValidationExceptions));
                    if (result) {
                        trueCounter.incrementAndGet();
                    } else {
                        falseCounter.incrementAndGet();
                    }
                    return result;
                }).
                filter(passwordValidationRule -> mandatoryRuleId.size() == trueCounter.get()).
                count();
        return (passwordValidatedCount == 1);
    }

    private static boolean applyValidationOnNumberOfRulesAndMandatoryRules(String password,
                                                                           int numberOfRulesApplied,
                                                                           List<Integer> mandatoryRuleId,
                                                                           PasswordValidationRule[] allPasswordRules,
                                                                           List<PasswordValidationException> passwordValidationExceptions) {
        boolean mandatoryValidationResult = applyValidationOnMandatoryRules(password, mandatoryRuleId, allPasswordRules, passwordValidationExceptions);
        if (mandatoryValidationResult) {
            AtomicInteger trueCounter = new AtomicInteger();
            AtomicInteger falseCounter = new AtomicInteger();
            int stopIfNumberOfFailureReached = (allPasswordRules.length) - (numberOfRulesApplied + mandatoryRuleId.size());

            long passwordValidatedCount = Stream.of(allPasswordRules).
                    filter(passwordValidationRule -> {
                        boolean result = (trueCounter.get() <= numberOfRulesApplied) &&
                                (falseCounter.get() <= stopIfNumberOfFailureReached) &&
                                !mandatoryRuleId.contains(passwordValidationRule.getId()) &&
                                passwordValidationRule.isValidPassword(password, passwordValidationExceptions);
                        if (result) {
                            trueCounter.incrementAndGet();
                        } else {
                            falseCounter.incrementAndGet();
                        }
                        return result;
                    }).
                    filter(passwordValidationRule -> numberOfRulesApplied == trueCounter.get()).
                    count();
            return (passwordValidatedCount == 1);
        }
        return mandatoryValidationResult;
    }
}