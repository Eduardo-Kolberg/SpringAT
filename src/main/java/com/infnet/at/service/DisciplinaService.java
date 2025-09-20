package com.infnet.at.service;

import com.infnet.at.model.Disciplina;
import com.infnet.at.repository.DisciplinaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DisciplinaService {
    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Transactional
    public Disciplina cadastrarDisciplina(@Valid Disciplina disciplina) {
        return disciplinaRepository.save(disciplina);
    }

    @Transactional(readOnly = true)
    public List<Disciplina> listarDisciplinas() {
        return disciplinaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Disciplina> buscarPorId(Long id) {
        return disciplinaRepository.findById(id);
    }
}
