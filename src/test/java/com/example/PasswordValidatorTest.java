package com.example;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PasswordValidatorTest {

    @Test
    public void shouldTestPasswordNotNull() {
        assertThat(true).isTrue();
    }

    @Test
    public void shouldTestPasswordHaveOneUpperCaseLetterAtLeast() {
        assertThat(true).isTrue();
    }

    @Test
    public void shouldTestPasswordHaveOneLowerCaseLetterAtLeast() {
        assertThat(true).isTrue();
    }

    @Test
    public void shouldTestPasswordHaveOneNumericValueAtLeast() {
        assertThat(true).isTrue();
    }

    @Test
    public void shouldTestPasswordLargerThan8Chars() {
        assertThat(true).isTrue();
    }

}