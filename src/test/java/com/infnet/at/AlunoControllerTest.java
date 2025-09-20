package com.infnet.at;

import com.infnet.at.controller.AlunoController;
import com.infnet.at.model.Aluno;
import com.infnet.at.service.AlunoService;
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
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AlunoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AlunoService alunoService;

    @InjectMocks
    private AlunoController alunoController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(alunoController).build();
    }

    @Test
    void testCadastrarAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("João");
        aluno.setCpf("12345678901");
        aluno.setEmail("joao@example.com");
        aluno.setTelefone("999999999");
        aluno.setEndereco("Rua A");

        when(alunoService.cadastrarAluno(ArgumentMatchers.any(Aluno.class))).thenReturn(aluno);

        mockMvc.perform(post("/alunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(aluno)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("João"));

        verify(alunoService, times(1)).cadastrarAluno(any(Aluno.class));
    }

    @Test
    void testListarAlunos() throws Exception {
        Aluno aluno1 = new Aluno(1L, "João", "12345678901", "joao@example.com", "999999999", "Rua A", null);
        Aluno aluno2 = new Aluno(2L, "Maria", "10987654321", "maria@example.com", "888888888", "Rua B", null);

        when(alunoService.listarAlunos()).thenReturn(Arrays.asList(aluno1, aluno2));

        mockMvc.perform(get("/alunos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].nome").value("João"))
                .andExpect(jsonPath("$[1].nome").value("Maria"));

        verify(alunoService, times(1)).listarAlunos();
    }

    @Test
    void testBuscarPorId_Existente() throws Exception {
        Aluno aluno = new Aluno(1L, "João", "12345678901", "joao@example.com", "999999999", "Rua A", null);

        when(alunoService.buscarPorId(1L)).thenReturn(Optional.of(aluno));

        mockMvc.perform(get("/alunos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João"));

        verify(alunoService, times(1)).buscarPorId(1L);
    }

    @Test
    void testBuscarPorId_NaoExistente() throws Exception {
        when(alunoService.buscarPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/alunos/1"))
                .andExpect(status().isNotFound());

        verify(alunoService, times(1)).buscarPorId(1L);
    }
}

