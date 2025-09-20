package com.infnet.at;

import com.infnet.at.model.Disciplina;
import com.infnet.at.repository.DisciplinaRepository;
import com.infnet.at.service.DisciplinaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DisciplinaServiceTest {

    @Mock
    private DisciplinaRepository disciplinaRepository;

    @InjectMocks
    private DisciplinaService disciplinaService;

    private Disciplina disciplina;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        disciplina = new Disciplina();
        disciplina.setId(1L);
        disciplina.setNome("Matemática");
        disciplina.setCodigo("MAT101");
    }

    @Test
    void testCadastrarDisciplina() {
        when(disciplinaRepository.save(disciplina)).thenReturn(disciplina);

        Disciplina resultado = disciplinaService.cadastrarDisciplina(disciplina);

        assertNotNull(resultado);
        assertEquals("Matemática", resultado.getNome());
        verify(disciplinaRepository, times(1)).save(disciplina);
    }

    @Test
    void testListarDisciplinas() {
        List<Disciplina> lista = Arrays.asList(disciplina);
        when(disciplinaRepository.findAll()).thenReturn(lista);

        List<Disciplina> resultado = disciplinaService.listarDisciplinas();

        assertEquals(1, resultado.size());
        verify(disciplinaRepository, times(1)).findAll();
    }

    @Test
    void testBuscarPorId_Found() {
        when(disciplinaRepository.findById(1L)).thenReturn(Optional.of(disciplina));

        Optional<Disciplina> resultado = disciplinaService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Matemática", resultado.get().getNome());
        verify(disciplinaRepository, times(1)).findById(1L);
    }

    @Test
    void testBuscarPorId_NotFound() {
        when(disciplinaRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Disciplina> resultado = disciplinaService.buscarPorId(2L);

        assertFalse(resultado.isPresent());
        verify(disciplinaRepository, times(1)).findById(2L);
    }
}


