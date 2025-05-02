package com.adrianobarbosa.sulwork_cafe.service;

import com.adrianobarbosa.sulwork_cafe.exception.BusinessException;
import com.adrianobarbosa.sulwork_cafe.model.ItemCafe;
import com.adrianobarbosa.sulwork_cafe.repository.ItemCafeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ItemCafeServiceTest {

    @Mock
    private ItemCafeRepository itemCafeRepository;

    @InjectMocks
    private ItemCafeService itemCafeService;

    private ItemCafe itemCafe;

    @BeforeEach
    public void setUp() {
        itemCafe = new ItemCafe();
        itemCafe.setNomeItem("Café");
        itemCafe.setDataCafe(LocalDate.now().plusDays(1));  // Café para amanhã
    }

    @Test
    public void testSalvarComSucesso() {
        // Arrange
        when(itemCafeRepository.findByNomeItemAndDataCafe(itemCafe.getNomeItem(), itemCafe.getDataCafe())).thenReturn(Optional.empty());
        when(itemCafeRepository.save(itemCafe)).thenReturn(itemCafe);

        // Act
        ItemCafe resultado = itemCafeService.salvar(itemCafe);

        // Assert
        assertNotNull(resultado);
        assertEquals(itemCafe.getNomeItem(), resultado.getNomeItem());
        verify(itemCafeRepository, times(1)).save(itemCafe);
    }

    @Test
    public void testSalvarItemJaExistente() {
        // Arrange
        when(itemCafeRepository.findByNomeItemAndDataCafe(itemCafe.getNomeItem(), itemCafe.getDataCafe())).thenReturn(Optional.of(itemCafe));

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            itemCafeService.salvar(itemCafe);
        });
        assertEquals("Item já cadastrado para essa data!", exception.getMessage());
    }

    @Test
    public void testSalvarDataPassada() {
        // Arrange
        itemCafe.setDataCafe(LocalDate.now().minusDays(1)); // Data passada

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            itemCafeService.salvar(itemCafe);
        });
        assertEquals("A data do café deve ser futura.", exception.getMessage());
    }
}