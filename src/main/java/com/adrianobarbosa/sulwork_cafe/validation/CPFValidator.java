package com.adrianobarbosa.sulwork_cafe.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CPFValidator implements ConstraintValidator<CPFValido, String> {

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        if (cpf == null || !cpf.matches("\\d{11}")) return false;

        // Elimina CPFs com todos os dígitos iguais
        if (cpf.matches("(\\d)\\1{10}")) return false;

        int[] pesos1 = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] pesos2 = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

        try {
            int d1 = calcularDigito(cpf.substring(0, 9), pesos1);
            int d2 = calcularDigito(cpf.substring(0, 9) + d1, pesos2);
            return cpf.equals(cpf.substring(0, 9) + d1 + d2);
        } catch (Exception e) {
            return false;
        }
    }

    private int calcularDigito(String str, int[] pesos) {
        int soma = 0;
        for (int i = 0; i < str.length(); i++) {
            soma += (str.charAt(i) - '0') * pesos[i];
        }
        int resto = soma % 11;
        return resto < 2 ? 0 : 11 - resto;
    }
}