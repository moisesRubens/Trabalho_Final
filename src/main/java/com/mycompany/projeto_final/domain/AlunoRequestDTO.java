package com.mycompany.projeto_final.domain;

public record AlunoRequestDTO(String matricula, String nome,
                              String dataNascimento, String cpf,
                              String telefone, int idade) {

    public AlunoRequestDTO(String matricula, String nome, String dataNascimento, String cpf, String telefone, int idade) {
        this.matricula = matricula;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.telefone = telefone;
        this.idade = idade;
    }
}
