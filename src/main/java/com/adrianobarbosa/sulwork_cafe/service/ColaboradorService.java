package com.adrianobarbosa.sulwork_cafe.service;

import com.adrianobarbosa.sulwork_cafe.exception.BusinessException;
import com.adrianobarbosa.sulwork_cafe.exception.CustomException;
import com.adrianobarbosa.sulwork_cafe.model.Colaborador;
import com.adrianobarbosa.sulwork_cafe.repository.ColaboradorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
public class ColaboradorService {

    private final ColaboradorRepository colaboradorRepository;

    public ColaboradorService(ColaboradorRepository colaboradorRepository) {
        this.colaboradorRepository = colaboradorRepository;
    }

    public Colaborador salvar(Colaborador colaborador) {
        if (colaborador.getCpf().length() != 11) {
            throw new CustomException("CPF deve ter 11 dígitos.");
        }

        Optional<Colaborador> existente = colaboradorRepository.findByCpf(colaborador.getCpf());
        if (existente.isPresent())  {
            throw new CustomException("CPF já cadastrado.");
        }

        return colaboradorRepository.save(colaborador);
    }

    @Transactional(readOnly = true)
    public List<Colaborador> listarTodos() {
        return colaboradorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Colaborador buscarPorId(Long id) {
        return colaboradorRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Colaborador não encontrado."));
    }
    @Transactional(readOnly = true)
    public Colaborador buscarPorCpf(String cpf) {
        return colaboradorRepository.findByCpf(cpf)
                .orElseThrow(() -> new BusinessException("Colaborador não encontrado"));
    }

    @Transactional
    public void excluir(Long id) {
        buscarPorId(id); // garante que existe antes de excluir
        colaboradorRepository.deleteById(id);
    }

}