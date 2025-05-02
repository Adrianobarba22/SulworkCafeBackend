package com.adrianobarbosa.sulwork_cafe.exception;

import com.adrianobarbosa.sulwork_cafe.controller.ColaboradorController;
import com.adrianobarbosa.sulwork_cafe.service.ColaboradorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ColaboradorController.class)
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ColaboradorService colaboradorService;

    @Test
    void deveRetornar400QuandoBusinessExceptionLancada() throws Exception {
        // Simula lançamento de BusinessException ao buscar colaborador com ID 1
        when(colaboradorService.buscarPorId(1L))
                .thenThrow(new BusinessException("Colaborador não encontrado."));

        mockMvc.perform(get("/api/colaboradores/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("Regra de negócio violada"))
                .andExpect(jsonPath("$.message").value("Colaborador não encontrado."));
    }

    @Test
    void deveRetornar400QuandoCustomExceptionLancada() throws Exception {
        // Simula lançamento de CustomException ao buscar colaborador com ID 2
        when(colaboradorService.buscarPorId(2L))
                .thenThrow(new CustomException("Erro de CPF inválido."));

        mockMvc.perform(get("/api/colaboradores/2"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("Erro personalizado"))
                .andExpect(jsonPath("$.message").value("Erro de CPF inválido."));
    }
}
