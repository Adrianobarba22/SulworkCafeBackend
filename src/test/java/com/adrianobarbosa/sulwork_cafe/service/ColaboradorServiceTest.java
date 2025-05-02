package com.adrianobarbosa.sulwork_cafe.service;

import com.adrianobarbosa.sulwork_cafe.exception.CustomException;
import com.adrianobarbosa.sulwork_cafe.model.Colaborador;
import com.adrianobarbosa.sulwork_cafe.repository.ColaboradorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ColaboradorServiceTest {

    @Mock
    private ColaboradorRepository colaboradorRepository;

    @InjectMocks
    private ColaboradorService colaboradorService;

    private Colaborador colaborador;

    @BeforeEach
    public void setUp() {
        colaborador = new Colaborador();
        colaborador.setCpf("77541211230");
        colaborador.setNome("Adriano Barbosa");
    }

    @Test
    public void testSalvarComCpfValido() {
        // Arrange
        when(colaboradorRepository.findByCpf(colaborador.getCpf())).thenReturn(Optional.empty());
        when(colaboradorRepository.save(colaborador)).thenReturn(colaborador);

        // Act
        Colaborador resultado = colaboradorService.salvar(colaborador);

        // Assert
        assertNotNull(resultado);
        assertEquals(colaborador.getCpf(), resultado.getCpf());
        verify(colaboradorRepository, times(1)).save(colaborador);
    }

    @Test
    public void testSalvarComCpfJaCadastrado() {
        // Arrange
        when(colaboradorRepository.findByCpf(colaborador.getCpf())).thenReturn(Optional.of(colaborador));

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> {
            colaboradorService.salvar(colaborador);
        });
        assertEquals("CPF já cadastrado.", exception.getMessage());
    }

    @Test
    public void testSalvarComCpfInvalido() {
        // Arrange
        colaborador.setCpf("12345"); // CPF inválido (menos de 11 dígitos)

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> {
            colaboradorService.salvar(colaborador);
        });
        assertEquals("CPF deve ter 11 dígitos.", exception.getMessage());
    }
}