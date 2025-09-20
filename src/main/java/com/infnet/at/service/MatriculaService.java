package com.infnet.at.service;

import com.infnet.at.model.Matricula;
import com.infnet.at.model.Aluno;
import com.infnet.at.repository.MatriculaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatriculaService {
    @Autowired
    private MatriculaRepository matriculaRepository;

    public Matricula alocarAluno(@Valid Matricula matricula) {
        return matriculaRepository.save(matricula);
    }

    public Matricula atribuirNota(Long matriculaId, Double nota) {
        Matricula matricula = matriculaRepository.findById(matriculaId).orElseThrow();
        matricula.setNota(nota);
        return matriculaRepository.save(matricula);
    }

    public List<Matricula> listarMatriculasPorDisciplina(Long disciplinaId) {
        return matriculaRepository.findByDisciplinaId(disciplinaId);
    }

    public List<Aluno> listarAprovados(Long disciplinaId) {
        return matriculaRepository.findByDisciplinaId(disciplinaId).stream()
                .filter(m -> m.getNota() >= 7)
                .map(Matricula::getAluno)
                .collect(Collectors.toList());
    }

    public List<Aluno> listarReprovados(Long disciplinaId) {
        return matriculaRepository.findByDisciplinaId(disciplinaId).stream()
                .filter(m -> m.getNota() < 7)
                .map(Matricula::getAluno)
                .collect(Collectors.toList());
    }
}
