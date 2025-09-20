package com.infnet.at;

import com.infnet.at.model.Aluno;
import com.infnet.at.repository.AlunoRepository;
import com.infnet.at.service.AlunoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AlunoServiceTest {

    @Mock
    private AlunoRepository alunoRepository;

    @InjectMocks
    private AlunoService alunoService;

    private Aluno aluno;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("João Silva");
        aluno.setCpf("12345678901");
        aluno.setEmail("joao@email.com");
        aluno.setTelefone("11999999999");
        aluno.setEndereco("Rua A, 123");
    }

    @Test
    void testCadastrarAluno() {
        when(alunoRepository.save(aluno)).thenReturn(aluno);

        Aluno resultado = alunoService.cadastrarAluno(aluno);

        assertNotNull(resultado);
        assertEquals("João Silva", resultado.getNome());
        verify(alunoRepository, times(1)).save(aluno);
    }

    @Test
    void testListarAlunos() {
        List<Aluno> lista = Arrays.asList(aluno);
        when(alunoRepository.findAll()).thenReturn(lista);

        List<Aluno> resultado = alunoService.listarAlunos();

        assertEquals(1, resultado.size());
        verify(alunoRepository, times(1)).findAll();
    }

    @Test
    void testBuscarPorId_Found() {
        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));

        Optional<Aluno> resultado = alunoService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("João Silva", resultado.get().getNome());
        verify(alunoRepository, times(1)).findById(1L);
    }

    @Test
    void testBuscarPorId_NotFound() {
        when(alunoRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Aluno> resultado = alunoService.buscarPorId(2L);

        assertFalse(resultado.isPresent());
        verify(alunoRepository, times(1)).findById(2L);
    }
}

