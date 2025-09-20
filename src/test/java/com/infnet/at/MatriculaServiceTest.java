package com.infnet.at;

import com.infnet.at.model.Matricula;
import com.infnet.at.model.Aluno;
import com.infnet.at.repository.MatriculaRepository;
import com.infnet.at.service.MatriculaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MatriculaServiceTest {

    @Mock
    private MatriculaRepository matriculaRepository;

    @InjectMocks
    private MatriculaService matriculaService;

    private Matricula matricula;
    private Aluno aluno;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("João");

        matricula = new Matricula();
        matricula.setId(1L);
        matricula.setAluno(aluno);
        matricula.setNota(5.0);
    }

    @Test
    void testAlocarAluno() {
        when(matriculaRepository.save(matricula)).thenReturn(matricula);

        Matricula resultado = matriculaService.alocarAluno(matricula);

        assertNotNull(resultado);
        assertEquals(aluno.getNome(), resultado.getAluno().getNome());
        verify(matriculaRepository, times(1)).save(matricula);
    }

    @Test
    void testAtribuirNota() {
        when(matriculaRepository.findById(1L)).thenReturn(Optional.of(matricula));
        when(matriculaRepository.save(any(Matricula.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Matricula resultado = matriculaService.atribuirNota(1L, 8.5);

        assertNotNull(resultado);
        assertEquals(8.5, resultado.getNota());
        verify(matriculaRepository, times(1)).findById(1L);
        verify(matriculaRepository, times(1)).save(matricula);
    }

    @Test
    void testListarMatriculasPorDisciplina() {
        List<Matricula> lista = Arrays.asList(matricula);
        when(matriculaRepository.findByDisciplinaId(1L)).thenReturn(lista);

        List<Matricula> resultado = matriculaService.listarMatriculasPorDisciplina(1L);

        assertEquals(1, resultado.size());
        verify(matriculaRepository, times(1)).findByDisciplinaId(1L);
    }

    @Test
    void testListarAprovados() {
        Matricula matriculaAprovada = new Matricula();
        matriculaAprovada.setId(2L);
        matriculaAprovada.setAluno(aluno);
        matriculaAprovada.setNota(7.0);

        List<Matricula> lista = Arrays.asList(matricula, matriculaAprovada);
        when(matriculaRepository.findByDisciplinaId(1L)).thenReturn(lista);

        List<Aluno> aprovados = matriculaService.listarAprovados(1L);

        assertEquals(1, aprovados.size());
        assertEquals(aluno.getNome(), aprovados.get(0).getNome());
    }

    @Test
    void testListarReprovados() {
        Matricula matriculaReprovada = new Matricula();
        matriculaReprovada.setId(3L);
        matriculaReprovada.setAluno(aluno);
        matriculaReprovada.setNota(6.9);

        List<Matricula> lista = Arrays.asList(matriculaReprovada);
        when(matriculaRepository.findByDisciplinaId(1L)).thenReturn(lista);

        List<Aluno> reprovados = matriculaService.listarReprovados(1L);

        assertEquals(1, reprovados.size());
        assertEquals(aluno.getNome(), reprovados.get(0).getNome());
    }
}

