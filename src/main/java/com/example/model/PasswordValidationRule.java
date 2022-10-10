package com.example.model;

import com.example.exception.PasswordValidationException;

public enum PasswordValidationRule {
    PASSWORD_AT_LEAST_8_CHARS(1, "password should be larger than 8 chars", "^\\w{8,}$"),
    PASSWORD_NON_NULL(2, "password should not be null", "^\\w{1,}$"),
    PASSWORD_WITH_UPPER_CASE_LETTER(3, "password should have one uppercase letter at least", ".*[A-Z].*"),
    PASSWORD_WITH_LOWER_CASE_LETTER(4, "password should have one lowercase letter at least", ".*[a-z].*"),
    PASSWORD_WITH_NUMBERIC(5, "password should have one number at least", "(.)*(\\d)(.)*");

    public int getId() {
        return id;
    }

    public String getRuleDescription() {
        return ruleDescription;
    }

    public String getRegex() {
        return regex;
    }

    private final int id;
    private final String ruleDescription;
    private final String regex;

    PasswordValidationRule(int id, String ruleDescription, String regex) {
        this.id = id;
        this.ruleDescription = ruleDescription;
        this.regex = regex;
    }

    public boolean isValidPassword(String password) {
        if (password == null) {
            throw new PasswordValidationException(this.id + ":" + "password should not be null");
        }
        boolean result = password.matches(this.regex);
        if (!result) {
            throw new PasswordValidationException(this.id + ":" + this.ruleDescription);
        }
        return result;
    }
}