package com.adrianobarbosa.sulwork_cafe.dto;

import jakarta.validation.constraints.NotBlank;
import com.adrianobarbosa.sulwork_cafe.validation.CPFValido;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ColaboradorDTO {

    private Long id;

    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @NotBlank(message = "O CPF é obrigatório.")
    @CPFValido
    private String cpf;
}
