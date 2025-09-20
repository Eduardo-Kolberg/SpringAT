package com.infnet.at.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String nome;
    @NotBlank
    @Pattern(regexp = "\\d{11}", message = "CPF must be 11 digits")
    private String cpf;
    @NotBlank
    @Email(message = "Email should be valid")
    private String email;
    @NotBlank
    private String telefone;
    @NotBlank
    private String endereco;

    @OneToMany(mappedBy = "aluno", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Matricula> matriculas;

}
