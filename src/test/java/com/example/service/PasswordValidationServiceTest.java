package com.example.service;

import com.example.service.impl.PasswordValidationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class PasswordValidationServiceTest {
    private static PasswordValidationService passwordValidationService;

    @BeforeAll
    public static void beforeAll() {
        passwordValidationService = new PasswordValidationServiceImpl();
    }

    @Test
    public void shouldPasswordValidForGivenNumberOfRulesAndAlsoMandatoryRule() {
        boolean result = passwordValidationService.isValid("password", 3, Collections.singletonList(4));
        assertThat(result).isTrue();
    }
}