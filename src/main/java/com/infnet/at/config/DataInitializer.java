package com.infnet.at.config;

import com.infnet.at.model.Professor;
import com.infnet.at.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        if (professorRepository.findByNome("admin").isEmpty()) {
            Professor professor = new Professor();
            professor.setNome("admin");
            professor.setSenha(passwordEncoder.encode("admin123"));
            professorRepository.save(professor);
            System.out.println("Professor admin criado com sucesso!");
        }
    }
}
