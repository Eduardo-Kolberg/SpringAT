package com.infnet.at.controller;

import com.infnet.at.model.Matricula;
import com.infnet.at.model.Aluno;
import com.infnet.at.service.MatriculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/matriculas")
public class MatriculaController {
    @Autowired
    private MatriculaService matriculaService;

    @PostMapping
    public ResponseEntity<Matricula> alocarAluno(@Valid @RequestBody Matricula matricula) {
        Matricula novaMatricula = matriculaService.alocarAluno(matricula);
        return ResponseEntity.status(201).body(novaMatricula);
    }

    @PutMapping("/{id}/nota")
    public ResponseEntity<Matricula> atribuirNota(@PathVariable Long id, @RequestParam Double nota) {
        Matricula atualizada = matriculaService.atribuirNota(id, nota);
        return ResponseEntity.ok(atualizada);
    }

    @GetMapping("/disciplina/{disciplinaId}/aprovados")
    public ResponseEntity<List<Aluno>> listarAprovados(@PathVariable Long disciplinaId) {
        try {
            List<Aluno> aprovados = matriculaService.listarAprovados(disciplinaId);
            return ResponseEntity.ok(aprovados);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/disciplina/{disciplinaId}/reprovados")
    public ResponseEntity<List<Aluno>> listarReprovados(@PathVariable Long disciplinaId) {
        return ResponseEntity.ok(matriculaService.listarReprovados(disciplinaId));
    }
}
