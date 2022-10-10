package com.example.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class PasswordValidationServiceTest {
    private static PasswordValidationService passwordValidationService;

    @BeforeAll
    public static void beforeAll(){
        passwordValidationService = new PasswordValidationService();
    }

    @Test
    public void shouldPasswordValidWithGivenNoOfRules(){
       boolean result =  passwordValidationService.isValid("password", 3);
       assertThat(result).isTrue();
    }
}