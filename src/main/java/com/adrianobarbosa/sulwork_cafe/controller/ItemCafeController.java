package com.adrianobarbosa.sulwork_cafe.controller;

import com.adrianobarbosa.sulwork_cafe.dto.ItemCafeDTO;
import com.adrianobarbosa.sulwork_cafe.model.Colaborador;
import com.adrianobarbosa.sulwork_cafe.model.ItemCafe;
import com.adrianobarbosa.sulwork_cafe.service.ColaboradorService;
import com.adrianobarbosa.sulwork_cafe.service.ItemCafeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.adrianobarbosa.sulwork_cafe.exception.BusinessException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/itens-cafe")
public class ItemCafeController {

    private final ItemCafeService itemCafeService;
    private final ColaboradorService colaboradorService;

    public ItemCafeController(ItemCafeService itemCafeService, ColaboradorService colaboradorService) {
        this.itemCafeService = itemCafeService;
        this.colaboradorService = colaboradorService;
    }

    @PostMapping
    public ItemCafeDTO criar(@RequestBody @Valid ItemCafeDTO dto) {
        if (dto.getDataCafe().isBefore(LocalDate.now())) {
            throw new BusinessException("A data do café deve ser maior que a data atual.");
        }

        // Verificar se a opção de café já foi trazida para a mesma data
        if (itemCafeService.existeItemNoCafe(dto.getDescricao(), dto.getDataCafe())) {
            throw new BusinessException("A opção de café já foi trazida para essa data.");
        }

        Colaborador colaborador = colaboradorService.buscarPorId(dto.getColaboradorId());

        ItemCafe item = ItemCafe.builder()
                .nomeItem(dto.getDescricao())
                .dataCafe(dto.getDataCafe())
                .status(dto.getStatus() != null ? dto.getStatus() : ItemCafe.StatusItem.PENDENTE)
                .colaborador(colaborador)
                .build();

        item = itemCafeService.salvar(item);

        return toDTO(item);
    }

    @GetMapping
    public List<ItemCafeDTO> listar() {
        return itemCafeService.listarTodos()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/por-data")
    public List<ItemCafeDTO> listarPorData(@RequestParam("data") LocalDate data) {
        return itemCafeService.listarPorData(data)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        itemCafeService.excluir(id);
    }

    private ItemCafeDTO toDTO(ItemCafe item) {
        return ItemCafeDTO.builder()
                .id(item.getId())
                .colaboradorId(item.getColaborador().getId())
                .descricao(item.getNomeItem())
                .dataCafe(item.getDataCafe())
                .status(item.getStatus())
                .build();
    }
}