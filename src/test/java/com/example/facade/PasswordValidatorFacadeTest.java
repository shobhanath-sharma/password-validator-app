package com.example.facade;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PasswordValidatorFacadeTest {

    private static PasswordValidatorFacade validatorFacadeTest;

    @BeforeAll
    public static void initBeforeAll(){
        validatorFacadeTest = new PasswordValidatorFacade();
    }

    @ParameterizedTest
    @ValueSource(strings = {"Abcdefgh", "ABCDEFGh", "ABC EFGh", "abcdefgh", "abc efgh", "1234567h", "1234 67h", "Abcd1234", "Ab", "A@#a12"})
    public void shouldValidatePasswordWithCorrectInput(String inputString){
        boolean result = validatorFacadeTest.isValid(inputString);
         assertThat(result).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "ABCDEFGH", "ABC EFG", "abcd", "12345678", "1234 67", "AB1234", "@!#$%^"})
    public void shouldNotValidatePasswordWithWrongInput(String inputString){
        assertThatThrownBy(() -> {
            validatorFacadeTest.isValid(inputString);
        }).isInstanceOf(PasswordValidationException.class)
                .hasMessageContaining("Password validation failed as it does not meet password validation rules");
    }
}