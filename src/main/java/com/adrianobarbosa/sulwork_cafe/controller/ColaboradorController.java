package com.adrianobarbosa.sulwork_cafe.controller;

import com.adrianobarbosa.sulwork_cafe.dto.ColaboradorDTO;
import com.adrianobarbosa.sulwork_cafe.exception.BusinessException;
import com.adrianobarbosa.sulwork_cafe.model.Colaborador;
import com.adrianobarbosa.sulwork_cafe.service.ColaboradorService;
import com.adrianobarbosa.sulwork_cafe.validation.CPFValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/colaboradores")
@RequiredArgsConstructor
public class ColaboradorController {

    private final ColaboradorService colaboradorService;

    @PostMapping
    public ColaboradorDTO criar(@RequestBody @Valid ColaboradorDTO dto) {
        if (!isCpfValido(dto.getCpf())) {
            throw new BusinessException("CPF inválido.");
        }

        Colaborador colaborador = Colaborador.builder()
                .nome(dto.getNome())
                .cpf(dto.getCpf())
                .build();
        colaborador = colaboradorService.salvar(colaborador);

        return toDTO(colaborador);
    }

    private boolean isCpfValido(String cpf) {
        // Lógica de validação do CPF
        return new CPFValidator().isValid(cpf, null);
    }

    @GetMapping
    public List<ColaboradorDTO> listar() {
        return colaboradorService.listarTodos()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ColaboradorDTO listarPorId(@PathVariable Long id) {
        // Busca o colaborador usando o serviço
        Colaborador colaborador = colaboradorService.buscarPorId(id);
        // Converte o colaborador para DTO e retorna
        return toDTO(colaborador);
    }

    @PutMapping("/{id}")
    public ColaboradorDTO atualizar(@PathVariable Long id, @RequestBody @Valid ColaboradorDTO dto) {
        // Busca o colaborador pelo id
        Colaborador colaborador = colaboradorService.buscarPorId(id);

        // Atualiza os dados do colaborador com os dados recebidos no DTO
        colaborador.setNome(dto.getNome());
        colaborador.setCpf(dto.getCpf());

        // Salva o colaborador atualizado
        colaborador = colaboradorService.salvar(colaborador);

        // Retorna o colaborador atualizado em formato DTO
        return toDTO(colaborador);
        }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        colaboradorService.excluir(id);
    }

    private ColaboradorDTO toDTO(Colaborador colaborador) {
        return ColaboradorDTO.builder()
                .id(colaborador.getId())
                .nome(colaborador.getNome())
                .cpf(colaborador.getCpf())
                .build();
    }
}
