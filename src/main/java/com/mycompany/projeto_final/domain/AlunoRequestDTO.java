package com.mycompany.projeto_final.domain;

import java.time.LocalDate;

public record AlunoRequestDTO(String matricula, String nome,
                              LocalDate dataNascimento, String cpf,
                              String telefone) {

    public AlunoRequestDTO(String matricula, String nome, LocalDate dataNascimento, String cpf, String telefone) {
        this.matricula = matricula;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.telefone = telefone;
    }
}
