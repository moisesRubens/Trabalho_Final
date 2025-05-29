package com.mycompany.projeto_final.domain;

import java.time.LocalDate;

public class Aluno {
    private String nome;
    private String matricula;
    private LocalDate dataNascimento;
    private String telefone;
    private String cpf;
    private int idade;

    public Aluno(String nome, String matricula, LocalDate dataNascimento, String telefone, String cpf) {
        this.nome = nome;
        this.matricula = matricula;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.cpf = cpf;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public int getIdade() {
        return idade;
    }
}
