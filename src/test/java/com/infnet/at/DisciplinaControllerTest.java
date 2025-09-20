package com.infnet.at;

import com.infnet.at.controller.DisciplinaController;
import com.infnet.at.model.Disciplina;
import com.infnet.at.service.DisciplinaService;
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
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DisciplinaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DisciplinaService disciplinaService;

    @InjectMocks
    private DisciplinaController disciplinaController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(disciplinaController).build();
    }

    @Test
    void testCadastrarDisciplina() throws Exception {
        Disciplina disciplina = new Disciplina();
        disciplina.setId(1L);
        disciplina.setNome("Matemática");
        disciplina.setCodigo("MAT101");

        when(disciplinaService.cadastrarDisciplina(ArgumentMatchers.any(Disciplina.class))).thenReturn(disciplina);

        mockMvc.perform(post("/disciplinas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(disciplina)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Matemática"));

        verify(disciplinaService, times(1)).cadastrarDisciplina(any(Disciplina.class));
    }

    @Test
    void testListarDisciplinas_ComResultados() throws Exception {
        Disciplina d1 = new Disciplina(1L, "Matemática", "MAT101", null);
        Disciplina d2 = new Disciplina(2L, "História", "HIS101", null);

        when(disciplinaService.listarDisciplinas()).thenReturn(Arrays.asList(d1, d2));

        mockMvc.perform(get("/disciplinas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].nome").value("Matemática"))
                .andExpect(jsonPath("$[1].nome").value("História"));

        verify(disciplinaService, times(1)).listarDisciplinas();
    }

    @Test
    void testListarDisciplinas_SemResultados() throws Exception {
        when(disciplinaService.listarDisciplinas()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/disciplinas"))
                .andExpect(status().isNoContent());

        verify(disciplinaService, times(1)).listarDisciplinas();
    }

    @Test
    void testBuscarPorId_Existente() throws Exception {
        Disciplina disciplina = new Disciplina(1L, "Matemática", "MAT101", null);

        when(disciplinaService.buscarPorId(1L)).thenReturn(Optional.of(disciplina));

        mockMvc.perform(get("/disciplinas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Matemática"));

        verify(disciplinaService, times(1)).buscarPorId(1L);
    }

    @Test
    void testBuscarPorId_NaoExistente() throws Exception {
        when(disciplinaService.buscarPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/disciplinas/1"))
                .andExpect(status().isNotFound());

        verify(disciplinaService, times(1)).buscarPorId(1L);
    }
}

