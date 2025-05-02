package com.adrianobarbosa.sulwork_cafe.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CPFValidatorTest {

    private CPFValidator validator;

    @BeforeEach
    void setup() {
        validator = new CPFValidator();
    }

    @Test
    void deveAceitarCpfValido() {
        assertTrue(validator.isValid("11144477735", null)); // CPF válido
    }

    @Test
    void deveRejeitarCpfInvalido() {
        assertFalse(validator.isValid("12345678900", null)); // CPF inválido
    }

    @Test
    void deveRejeitarCpfNuloOuVazio() {
        assertFalse(validator.isValid(null, null));
        assertFalse(validator.isValid("", null));
    }
}
