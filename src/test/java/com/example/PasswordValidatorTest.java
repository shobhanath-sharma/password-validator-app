package com.example;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PasswordValidatorTest {

    @Test
    public void shouldTestPasswordNotNull() {
        assertThat(true).isTrue();
        assertThat(isValid("A", "^\\w{1,}$")).isTrue();
        assertThat(isValid("", "^\\w{1,}$")).isFalse();
    }

    @Test
    public void shouldTestPasswordHaveOneUpperCaseLetterAtLeast() {
        assertThat(true).isTrue();
        assertThat(isValid("A", "(?=.*[A-Z])")).isTrue();
        assertThat(isValid("a", "(?=.*[A-Z])")).isFalse();
    }

    @Test
    public void shouldTestPasswordHaveOneLowerCaseLetterAtLeast() {
        assertThat(true).isTrue();
        assertThat(isValid("a", "(?=.*[a-z])")).isTrue();
        assertThat(isValid("A", "(?=.*[a-z])")).isFalse();
    }

    @Test
    public void shouldTestPasswordHaveOneNumericValueAtLeast() {
        assertThat(true).isTrue();
        assertThat(isValid("A1b", "(?=.*\\d) ")).isTrue();
        assertThat(isValid("Abc", "(?=.*\\d) ")).isFalse();
    }

    @Test
    public void shouldTestPasswordLargerThan8Chars() {
        assertThat(true).isTrue();
        assertThat(isValid("abcdefgh", "^\\w{8,}$")).isTrue();
        assertThat(isValid("abcde", "^\\w{8,}$")).isFalse();
    }

    private boolean isValid(String password, String regex){
        if(password==null || password.length()==0){
            return false;
        }
        return password.matches(regex);
    }

}