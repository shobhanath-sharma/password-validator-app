package com.example.model;

import com.example.exception.PasswordValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.ArrayList;
import java.util.List;

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
        List<PasswordValidationException> passwordValidationExceptions = new ArrayList<>();
        assertThat(PASSWORD_NON_NULL.isValid("A", passwordValidationExceptions)).isTrue();
        assertThat(passwordValidationExceptions).isEmpty();
    }

    @Test
    public void shouldTestPasswordHaveOneUpperCaseLetterAtLeast() {
        List<PasswordValidationException> passwordValidationExceptions = new ArrayList<>();
        assertThat(PASSWORD_WITH_UPPER_CASE_LETTER.isValid("A", passwordValidationExceptions)).isTrue();
        assertThat(passwordValidationExceptions).isEmpty();
    }

    @Test
    public void shouldTestPasswordHaveOneLowerCaseLetterAtLeast() {
        List<PasswordValidationException> passwordValidationExceptions = new ArrayList<>();
        assertThat(PASSWORD_WITH_LOWER_CASE_LETTER.isValid("a", passwordValidationExceptions)).isTrue();
        assertThat(passwordValidationExceptions).isEmpty();
    }

    @Test
    public void shouldTestPasswordHaveOneNumericValueAtLeast() {
        List<PasswordValidationException> passwordValidationExceptions = new ArrayList<>();
        assertThat(PASSWORD_WITH_NUMBERIC.isValid("A1b", passwordValidationExceptions)).isTrue();
        assertThat(passwordValidationExceptions).isEmpty();
    }

    @Test
    public void shouldTestPasswordLargerThan8Chars() {
        List<PasswordValidationException> passwordValidationExceptions = new ArrayList<>();
        assertThat(PASSWORD_AT_LEAST_8_CHARS.isValid("abcdefgh", passwordValidationExceptions)).isTrue();
        assertThat(passwordValidationExceptions).isEmpty();
    }

    @ParameterizedTest
    @EnumSource(PasswordValidationRule.class)
    void shouldThrowExceptionWhenPasswordRuleDoesNotPassGivenPassword(PasswordValidationRule passwordValidationRule) {
        List<PasswordValidationException> passwordValidationExceptions = new ArrayList<>();
        switch (passwordValidationRule) {
            case PASSWORD_AT_LEAST_8_CHARS:
                assertThat(passwordValidationRule.isValid("Test", passwordValidationExceptions)).isFalse();
                assertThatThrownBy(() -> {
                    throw passwordValidationExceptions.get(0);
                }).isInstanceOf(PasswordValidationException.class)
                        .hasMessageContaining("password should be larger than 8 chars");
                break;
            case PASSWORD_NON_NULL:
                assertThat(passwordValidationRule.isValid("", passwordValidationExceptions)).isFalse();
                assertThatThrownBy(() -> {
                    throw passwordValidationExceptions.get(0);
                }).isInstanceOf(PasswordValidationException.class)
                        .hasMessageContaining("password should not be null");
                break;
            case PASSWORD_WITH_UPPER_CASE_LETTER:
                assertThat(passwordValidationRule.isValid("a123", passwordValidationExceptions)).isFalse();
                assertThatThrownBy(() -> {
                    throw passwordValidationExceptions.get(0);
                }).isInstanceOf(PasswordValidationException.class)
                        .hasMessageContaining("password should have one uppercase letter at least");
                break;
            case PASSWORD_WITH_LOWER_CASE_LETTER:
                assertThat(passwordValidationRule.isValid("T123", passwordValidationExceptions)).isFalse();
                assertThatThrownBy(() -> {
                    throw passwordValidationExceptions.get(0);
                }).isInstanceOf(PasswordValidationException.class)
                        .hasMessageContaining("password should have one lowercase letter at least");
                break;
            case PASSWORD_WITH_NUMBERIC:
                assertThat(passwordValidationRule.isValid("test", passwordValidationExceptions)).isFalse();
                assertThatThrownBy(() -> {
                    throw passwordValidationExceptions.get(0);
                }).isInstanceOf(PasswordValidationException.class)
                        .hasMessageContaining("password should have one number at least");
                break;

            default:
                fail("Unknown enums and not yet declared in test class");
        }
    }

}
