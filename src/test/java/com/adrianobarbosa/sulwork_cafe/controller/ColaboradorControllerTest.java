package com.adrianobarbosa.sulwork_cafe.controller;


import com.adrianobarbosa.sulwork_cafe.exception.BusinessException;
import com.adrianobarbosa.sulwork_cafe.model.Colaborador;
import com.adrianobarbosa.sulwork_cafe.service.ColaboradorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.Arrays;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(ColaboradorController.class)
class ColaboradorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ColaboradorService colaboradorService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("Deve listar todos os colaboradores")
    void deveListarTodosColaboradores() throws Exception {
        Colaborador c1 = Colaborador.builder().id(1L).nome("Maria").cpf("12345678901").build();
        Colaborador c2 = Colaborador.builder().id(2L).nome("José").cpf("98765432100").build();

        when(colaboradorService.listarTodos()).thenReturn(Arrays.asList(c1, c2));

        mockMvc.perform(get("/api/colaboradores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome", is("Maria")))
                .andExpect(jsonPath("$[1].cpf", is("98765432100")));
    }

    @Test
    @DisplayName("Deve buscar colaborador por ID")
    void deveBuscarColaboradorPorId() throws Exception {
        Colaborador colaborador = Colaborador.builder().id(1L).nome("Carlos").cpf("12345678901").build();

        when(colaboradorService.buscarPorId(1L)).thenReturn(colaborador);

        mockMvc.perform(get("/api/colaboradores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Carlos")))
                .andExpect(jsonPath("$.cpf", is("12345678901")));
    }

    @Test
    @DisplayName("Deve excluir colaborador")
    void deveExcluirColaborador() throws Exception {
        Mockito.doNothing().when(colaboradorService).excluir(1L);

        mockMvc.perform(delete("/api/colaboradores/1"))
                .andExpect(status().isOk());
    }

}
