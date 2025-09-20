package com.infnet.at.controller;

import com.infnet.at.model.Disciplina;
import com.infnet.at.service.DisciplinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/disciplinas")
public class DisciplinaController {
    @Autowired
    private DisciplinaService disciplinaService;

    @PostMapping
    public ResponseEntity<Disciplina> cadastrarDisciplina(@Valid @RequestBody Disciplina disciplina) {
        Disciplina novaDisciplina = disciplinaService.cadastrarDisciplina(disciplina);
        return ResponseEntity.status(201).body(novaDisciplina);
    }

    @GetMapping
    public ResponseEntity<List<Disciplina>> listarDisciplinas() {
        try {
            List<Disciplina> disciplinas = disciplinaService.listarDisciplinas();
            if (disciplinas.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(disciplinas);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Disciplina> buscarPorId(@PathVariable Long id) {
        return disciplinaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
