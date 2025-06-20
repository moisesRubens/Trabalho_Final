package com.mycompany.projeto_final.domain;

import java.time.LocalDate;

public record AlunoResponseDTO(String matricula, String nome,
                              LocalDate dataNascimento, String cpf,
                              String telefone, int idade) {

    public AlunoResponseDTO(String matricula, String nome, LocalDate dataNascimento, String cpf, String telefone, int idade) {
        this.matricula = matricula;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.telefone = telefone;
        this.idade = idade;
    }
}
