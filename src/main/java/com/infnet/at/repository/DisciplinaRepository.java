package com.infnet.at.repository;

import com.infnet.at.model.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    Optional<Disciplina> findByCodigo(String codigo);
}
