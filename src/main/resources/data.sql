-- Inserindo Disciplinas
INSERT INTO disciplina (codigo, nome)
SELECT 'ES001', 'Engenharia de Software'
WHERE NOT EXISTS (SELECT 1 FROM disciplina WHERE codigo = 'ES001');

INSERT INTO disciplina (codigo, nome)
SELECT 'MD001', 'Matematica Discreta'
WHERE NOT EXISTS (SELECT 1 FROM disciplina WHERE codigo = 'MD001');

-- Inserindo Alunos
INSERT INTO aluno (nome, cpf, email, telefone, endereco)
SELECT 'João Silva', '12345678901', 'joao@email.com', '21999999999', 'Rua A, 123'
WHERE NOT EXISTS (SELECT 1 FROM aluno WHERE cpf = '12345678901');

INSERT INTO aluno (nome, cpf, email, telefone, endereco)
SELECT 'Maria Santos', '98765432101', 'maria@email.com', '21988888888', 'Rua B, 456'
WHERE NOT EXISTS (SELECT 1 FROM aluno WHERE cpf = '98765432101');

-- Inserindo Matrículas
INSERT INTO matricula (aluno_id, disciplina_id, nota)
SELECT a.id, d.id, 8.5
FROM aluno a, disciplina d
WHERE a.cpf = '12345678901' AND d.codigo = 'ES001'
AND NOT EXISTS (
    SELECT 1 FROM matricula m
    WHERE m.aluno_id = a.id AND m.disciplina_id = d.id
);

INSERT INTO matricula (aluno_id, disciplina_id, nota)
SELECT a.id, d.id, 5.0
FROM aluno a, disciplina d
WHERE a.cpf = '98765432101' AND d.codigo = 'ES001'
AND NOT EXISTS (
    SELECT 1 FROM matricula m
    WHERE m.aluno_id = a.id AND m.disciplina_id = d.id
);
