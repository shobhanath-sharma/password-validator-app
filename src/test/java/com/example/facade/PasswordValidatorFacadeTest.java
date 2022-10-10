package com.example.facade;

import com.example.exception.PasswordValidationException;
import com.example.service.PasswordValidationService;
import com.example.service.impl.PasswordValidationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PasswordValidatorFacadeTest {

    private static PasswordValidatorFacade passwordValidatorFacade;

    @BeforeAll
    public static void initBeforeAll() {
        PasswordValidationService passwordValidationService = new PasswordValidationServiceImpl();
        passwordValidatorFacade = new PasswordValidatorFacade(passwordValidationService);
        assertThat(passwordValidationService).isNotNull();
        assertThat(passwordValidatorFacade).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {"abcdefgh", "1234567h", "Abcd1234", "Ab"})
    public void shouldValidatePasswordWithCorrectInput(String inputString) {
        boolean result = passwordValidatorFacade.isValid(inputString);
        assertThat(result).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "ABCDEFGH", "ABC EFG", "abcd", "12345678", "1234 67", "AB1234", "@!#$%^"})
    public void shouldNotValidatePasswordWithWrongInput(String inputString) {
        assertThatThrownBy(() -> {
            passwordValidatorFacade.isValid(inputString);
        }).isInstanceOf(PasswordValidationException.class)
                .hasMessageContaining("password should");
    }
}