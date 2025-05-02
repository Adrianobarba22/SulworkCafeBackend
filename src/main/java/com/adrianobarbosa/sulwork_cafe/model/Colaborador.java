package com.adrianobarbosa.sulwork_cafe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "colaboradores", uniqueConstraints = {
        @UniqueConstraint(columnNames = "cpf")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Colaborador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @NotBlank(message = "O CPF é obrigatório.")
    @Size(min = 11, max = 11, message = "O CPF deve conter exatamente 11 dígitos.")
    @Pattern(regexp = "\\d{11}", message = "O CPF deve conter apenas números.")
    @Column(unique = true, nullable = false, length = 11)
    private String cpf;

    @OneToMany(mappedBy = "colaborador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCafe> itensCafe;
}
