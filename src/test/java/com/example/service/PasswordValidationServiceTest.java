package com.example.service;

import com.example.exception.PasswordValidationException;
import com.example.service.impl.PasswordValidationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PasswordValidationServiceTest {
    private static PasswordValidationService passwordValidationService;

    @BeforeAll
    public static void beforeAll() {
        passwordValidationService = new PasswordValidationServiceImpl();
    }

    @Test
    public void shouldPasswordValidWithAtleastGivenNoOfRulesAndAlsoMandatoryRule() {
        List<PasswordValidationException> passwordValidationExceptions = new ArrayList<>();
        boolean result = passwordValidationService.isValid("password", 3, Collections.singletonList(4), passwordValidationExceptions);
        assertThat(result).isTrue();
    }
}