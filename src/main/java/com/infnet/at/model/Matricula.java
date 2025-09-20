package com.infnet.at.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Matricula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "aluno_id")
    @JsonBackReference(value = "aluno-matricula")
    private Aluno aluno;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference(value = "disciplina-matricula")
    @JoinColumn(name = "disciplina_id")
    private Disciplina disciplina;

    @NotNull
    private Double nota;
}