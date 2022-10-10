package com.example.service.impl;

import com.example.model.PasswordValidationRule;
import com.example.service.PasswordValidationService;

import java.util.List;
import java.util.stream.Stream;

public class PasswordValidationServiceImpl implements PasswordValidationService {
    @Override
    public boolean isValid(String password, int numberOfRulesApplied, List<Integer> mandatoryRuleId) {
        if (numberOfRulesApplied == 0) {
            return true;
        }
        //TODO: Need to extract and do some cleanup activity here
        //Calculate and consider both numberOfRulesApplied and mandatoryRuleId
        if (mandatoryRuleId != null && !mandatoryRuleId.isEmpty() && numberOfRulesApplied > mandatoryRuleId.size()) {
            numberOfRulesApplied = (numberOfRulesApplied - mandatoryRuleId.size());
            long mandatoryPasswordValidatedCount = mandatoryRuleId.stream().filter(ruleId -> Stream.of(PasswordValidationRule.values()).anyMatch(rule -> (ruleId == rule.getId()) && rule.isValidPassword(password))).count();
            long passwordValidatedCount = Stream.of(PasswordValidationRule.values()).filter(passwordValidationRule -> !mandatoryRuleId.contains(passwordValidationRule.getId()) && passwordValidationRule.isValidPassword(password)).count();
            return (mandatoryPasswordValidatedCount >= mandatoryRuleId.size() && passwordValidatedCount >= numberOfRulesApplied);
        }
        // Consider only mandatoryRuleId
        else if (mandatoryRuleId != null && !mandatoryRuleId.isEmpty() && numberOfRulesApplied <= mandatoryRuleId.size()) {
            long passwordValidatedCount = mandatoryRuleId.stream().filter(ruleId -> Stream.of(PasswordValidationRule.values()).anyMatch(rule -> (ruleId == rule.getId()) && rule.isValidPassword(password))).count();
            return passwordValidatedCount >= mandatoryRuleId.size();
        }
        //consider only numberOfRulesApplied
        else {
            long passwordValidatedCount = Stream.of(PasswordValidationRule.values()).filter(passwordValidationRule -> passwordValidationRule.isValidPassword(password)).count();
            return passwordValidatedCount >= numberOfRulesApplied;
        }
    }
}