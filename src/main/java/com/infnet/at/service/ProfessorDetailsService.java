package com.infnet.at.service;

import com.infnet.at.model.Professor;
import com.infnet.at.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfessorDetailsService implements UserDetailsService {
    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Professor professor = professorRepository.findByNome(username)
                .orElseThrow(() -> new UsernameNotFoundException("Professor not found"));

        return User.builder()
                .username(professor.getNome())
                .password(professor.getSenha())
                .roles("PROFESSOR")
                .build();
    }
}
