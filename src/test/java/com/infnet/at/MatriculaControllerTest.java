package com.infnet.at;

import com.infnet.at.controller.MatriculaController;
import com.infnet.at.model.Aluno;
import com.infnet.at.model.Matricula;
import com.infnet.at.service.MatriculaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class MatriculaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MatriculaService matriculaService;

    @InjectMocks
    private MatriculaController matriculaController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(matriculaController).build();
    }

    @Test
    void testAlocarAluno() throws Exception {
        Matricula matricula = new Matricula();
        matricula.setId(1L);
        matricula.setNota(0.0);
        // for simplicity, skip setting aluno and disciplina objects here

        when(matriculaService.alocarAluno(ArgumentMatchers.any(Matricula.class))).thenReturn(matricula);

        mockMvc.perform(post("/matriculas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(matricula)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nota").value(0.0));

        verify(matriculaService, times(1)).alocarAluno(any(Matricula.class));
    }

    @Test
    void testAtribuirNota() throws Exception {
        Matricula matriculaAtualizada = new Matricula();
        matriculaAtualizada.setId(1L);
        matriculaAtualizada.setNota(8.5);

        when(matriculaService.atribuirNota(1L, 8.5)).thenReturn(matriculaAtualizada);

        mockMvc.perform(put("/matriculas/1/nota")
                        .param("nota", "8.5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nota").value(8.5));

        verify(matriculaService, times(1)).atribuirNota(1L, 8.5);
    }

    @Test
    void testListarAprovados_ComResultados() throws Exception {
        Aluno aluno1 = new Aluno();
        aluno1.setId(1L);
        aluno1.setNome("Aluno A");

        Aluno aluno2 = new Aluno();
        aluno2.setId(2L);
        aluno2.setNome("Aluno B");

        List<Aluno> aprovados = Arrays.asList(aluno1, aluno2);

        when(matriculaService.listarAprovados(1L)).thenReturn(aprovados);

        mockMvc.perform(get("/matriculas/disciplina/1/aprovados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].nome").value("Aluno A"))
                .andExpect(jsonPath("$[1].nome").value("Aluno B"));

        verify(matriculaService, times(1)).listarAprovados(1L);
    }

    @Test
    void testListarAprovados_Exception() throws Exception {
        when(matriculaService.listarAprovados(1L)).thenThrow(new RuntimeException("Erro inesperado"));

        mockMvc.perform(get("/matriculas/disciplina/1/aprovados"))
                .andExpect(status().isNotFound());

        verify(matriculaService, times(1)).listarAprovados(1L);
    }

    @Test
    void testListarReprovados() throws Exception {
        Aluno aluno1 = new Aluno();
        aluno1.setId(1L);
        aluno1.setNome("Aluno C");

        when(matriculaService.listarReprovados(1L)).thenReturn(Collections.singletonList(aluno1));

        mockMvc.perform(get("/matriculas/disciplina/1/reprovados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].nome").value("Aluno C"));

        verify(matriculaService, times(1)).listarReprovados(1L);
    }
}

