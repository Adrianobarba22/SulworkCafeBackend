package com.adrianobarbosa.sulwork_cafe.service;

import com.adrianobarbosa.sulwork_cafe.exception.BusinessException;
import com.adrianobarbosa.sulwork_cafe.model.ItemCafe;
import com.adrianobarbosa.sulwork_cafe.model.ItemCafe.StatusItem;
import com.adrianobarbosa.sulwork_cafe.repository.ItemCafeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemCafeService {

    private final ItemCafeRepository itemCafeRepository;


    @Transactional
    public ItemCafe salvar(ItemCafe itemCafe) {
        if (itemCafeRepository.findByNomeItemAndDataCafe(itemCafe.getNomeItem(), itemCafe.getDataCafe()).isPresent()) {
            throw new BusinessException("Item já cadastrado para essa data!");
        }

        if (itemCafe.getDataCafe().isBefore(LocalDate.now())) {
            throw new BusinessException("A data do café deve ser futura.");
        }

        return itemCafeRepository.save(itemCafe);
    }

    @Transactional(readOnly = true)
    public List<ItemCafe> listarTodos() {
        return itemCafeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<ItemCafe> listarPorData(LocalDate data) {
        return itemCafeRepository.findAllByDataCafe(data);
    }

    @Transactional
    public void excluir(Long id) {
        ItemCafe itemCafe = itemCafeRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Item de café não encontrado."));
        itemCafeRepository.delete(itemCafe);
    }

    public boolean existeItemNoCafe(String nomeItem, LocalDate dataCafe) {
        return itemCafeRepository.existsByNomeItemAndDataCafe(nomeItem, dataCafe);
    }

    // Método para atualizar o status do item de café
    @Transactional
    // Método 1: Atualiza um único item — usado internamente
    public void atualizarStatusItemCafe(ItemCafe itemCafe) {
        LocalDate hoje = LocalDate.now();

        if (itemCafe.getDataCafe().isBefore(hoje) && itemCafe.getStatus() == StatusItem.PENDENTE) {
            itemCafe.setStatus(StatusItem.NAO_TROUXE);
        }
    }

    // Método 2: Atualiza todos os vencidos — usado pelo Scheduler
    public void atualizarStatusItensVencidos() {
        LocalDate dataAtual = LocalDate.now();
        List<ItemCafe> itens = itemCafeRepository.findAllByDataCafeBeforeAndStatus(dataAtual, StatusItem.PENDENTE);

        for (ItemCafe item : itens) {
            item.setStatus(StatusItem.NAO_TROUXE);
            itemCafeRepository.save(item);
        }
    }
}


