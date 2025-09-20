package com.infnet.at.repository;

import com.infnet.at.model.Matricula;
import com.infnet.at.model.Aluno;
import com.infnet.at.model.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
    List<Matricula> findByDisciplinaId(Long disciplinaId);
    List<Matricula> findByAlunoId(Long alunoId);
    Optional<Matricula> findByAlunoAndDisciplina(Aluno aluno, Disciplina disciplina);
}
