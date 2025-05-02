package com.adrianobarbosa.sulwork_cafe.dto;

import com.adrianobarbosa.sulwork_cafe.model.ItemCafe.StatusItem;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ItemCafeDTO {

    private Long id;

    @NotNull(message = "O ID do colaborador é obrigatório.")
    private Long colaboradorId;

    @NotBlank(message = "O nome do item é obrigatório.")
    private String descricao;

    @NotNull(message = "A data do café da manhã é obrigatória.")
    @FutureOrPresent(message = "A data do café deve ser hoje ou futura.")
    private LocalDate dataCafe;


    private StatusItem status;

    public LocalDate getDataCafe() {
        return dataCafe;
    }

    public void setDataCafe(LocalDate dataCafe) {
        this.dataCafe = dataCafe;
    }
}

