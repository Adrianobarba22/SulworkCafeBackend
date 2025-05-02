package com.adrianobarbosa.sulwork_cafe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "itens_cafe", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nome_item", "data_cafe"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemCafe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do item é obrigatório.")
    @Column(name = "nome_item", nullable = false)
    private String nomeItem;

    @NotNull(message = "A data do café da manhã é obrigatória.")
    @FutureOrPresent(message = "A data do café da manhã deve ser futura.")
    @Column(name = "data_cafe", nullable = false)
    private LocalDate dataCafe;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false) // Melhor prática: lazy para evitar carregar colaborador desnecessariamente
    private StatusItem status = StatusItem.PENDENTE; // Inicialização padrão segura

    @ManyToOne(fetch = FetchType.LAZY) // Melhor prática: lazy para evitar carregar colaborador desnecessariamente
    @JoinColumn(name = "colaborador_id", nullable = false)
    private Colaborador colaborador;

    public enum StatusItem {
        PENDENTE,
        TROUXE,
        NAO_TROUXE
    }
}
