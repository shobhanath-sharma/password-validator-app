package com.example.model;

import com.example.exception.PasswordValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static com.example.model.PasswordValidationRule.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.fail;

public class PasswordValidationRuleTest {

    @Test
    public void shouldCountNumberOfValidationRulesAvailable() {
        assertThat(PasswordValidationRule.values()).isNotEmpty();
    }

    @ParameterizedTest
    @EnumSource(PasswordValidationRule.class)
    void shouldTestPasswordRuleDescriptionAndRegexAssociated(PasswordValidationRule passwordValidationRule) {
        switch (passwordValidationRule) {
            case PASSWORD_AT_LEAST_8_CHARS:
                assertThat(passwordValidationRule.getRuleDescription()).isEqualTo("password should be larger than 8 chars");
                assertThat(passwordValidationRule.getRegex()).isEqualTo("^\\w{8,}$");
                break;
            case PASSWORD_NON_NULL:
                assertThat(passwordValidationRule.getRuleDescription()).isEqualTo("password should not be null");
                assertThat(passwordValidationRule.getRegex()).isEqualTo("^\\w{1,}$");
                break;
            case PASSWORD_WITH_UPPER_CASE_LETTER:
                assertThat(passwordValidationRule.getRuleDescription()).isEqualTo("password should have one uppercase letter at least");
                assertThat(passwordValidationRule.getRegex()).isEqualTo(".*[A-Z].*");
                break;
            case PASSWORD_WITH_LOWER_CASE_LETTER:
                assertThat(passwordValidationRule.getRuleDescription()).isEqualTo("password should have one lowercase letter at least");
                assertThat(passwordValidationRule.getRegex()).isEqualTo(".*[a-z].*");
                break;
            case PASSWORD_WITH_NUMBERIC:
                assertThat(passwordValidationRule.getRuleDescription()).isEqualTo("password should have one number at least");
                assertThat(passwordValidationRule.getRegex()).isEqualTo("(.)*(\\d)(.)*");
                break;
            default:
                fail("Unknown enums and not yet declared in test class");
        }
    }

    @Test
    public void shouldTestPasswordNotNull() {
        PasswordValidationRule passwordValidationRule = PASSWORD_NON_NULL;
        assertThat(passwordValidationRule.isValidPassword("A")).isTrue();
    }

    @Test
    public void shouldTestPasswordHaveOneUpperCaseLetterAtLeast() {
        PasswordValidationRule passwordValidationRule = PASSWORD_WITH_UPPER_CASE_LETTER;
        assertThat(passwordValidationRule.isValidPassword("A")).isTrue();
    }

    @Test
    public void shouldTestPasswordHaveOneLowerCaseLetterAtLeast() {
        PasswordValidationRule passwordValidationRule = PASSWORD_WITH_LOWER_CASE_LETTER;
        assertThat(passwordValidationRule.isValidPassword("a")).isTrue();
    }

    @Test
    public void shouldTestPasswordHaveOneNumericValueAtLeast() {
        PasswordValidationRule passwordValidationRule = PASSWORD_WITH_NUMBERIC;
        assertThat(passwordValidationRule.isValidPassword("A1b")).isTrue();
    }

    @Test
    public void shouldTestPasswordLargerThan8Chars() {
        PasswordValidationRule passwordValidationRule = PASSWORD_AT_LEAST_8_CHARS;
        assertThat(passwordValidationRule.isValidPassword("abcdefgh")).isTrue();
    }

    @ParameterizedTest
    @EnumSource(PasswordValidationRule.class)
    void shouldThrowExceptionWhenPasswordRuleDoesNotPassGivenPassword(PasswordValidationRule passwordValidationRule) {
        switch (passwordValidationRule) {
            case PASSWORD_AT_LEAST_8_CHARS:
                assertThatThrownBy(() -> {
                    passwordValidationRule.isValidPassword("Test");
                }).isInstanceOf(PasswordValidationException.class)
                        .hasMessageContaining("password should be larger than 8 chars");
                break;
            case PASSWORD_NON_NULL:
                assertThatThrownBy(() -> {
                    passwordValidationRule.isValidPassword("");
                }).isInstanceOf(PasswordValidationException.class)
                        .hasMessageContaining("password should not be null");
                break;
            case PASSWORD_WITH_UPPER_CASE_LETTER:
                assertThatThrownBy(() -> {
                    passwordValidationRule.isValidPassword("a123");
                }).isInstanceOf(PasswordValidationException.class)
                        .hasMessageContaining("password should have one uppercase letter at least");
                break;
            case PASSWORD_WITH_LOWER_CASE_LETTER:
                assertThatThrownBy(() -> {
                    passwordValidationRule.isValidPassword("T123");
                }).isInstanceOf(PasswordValidationException.class)
                        .hasMessageContaining("password should have one lowercase letter at least");
                break;
            case PASSWORD_WITH_NUMBERIC:
                assertThatThrownBy(() -> {
                    passwordValidationRule.isValidPassword("test");
                }).isInstanceOf(PasswordValidationException.class)
                        .hasMessageContaining("password should have one number at least");
                break;
            default:
                fail("Unknown enums and not yet declared in test class");
        }
    }

}
