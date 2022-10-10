package com.example.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PasswordValidationModelTest {

    @Test
    public void shouldCountNumberOfValidationRulesAvailable(){
       assertThat(PasswordValidationModel.values()).isNotEmpty();
   }
}
