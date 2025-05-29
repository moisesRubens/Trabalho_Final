
package com.mycompany.projeto_final.controller;

import com.mycompany.projeto_final.domain.Aluno;
import com.mycompany.projeto_final.domain.AlunoRequestDTO;
import com.mycompany.projeto_final.service.AlunoService;

public class AlunoController {
    
    public static String cadastrarAluno(AlunoRequestDTO dadosAluno) {
        Aluno aluno = new Aluno(dadosAluno.nome(), dadosAluno.matricula(), dadosAluno.dataNascimento(), 
                                dadosAluno.telefone(), dadosAluno.cpf(), dadosAluno.idade());
        String mensagem = "ALUNO NÃO CADASTRADO";
        
        if(AlunoService.addAluno(aluno)) {
            mensagem = "ALUNO CADASTRADO";
        }
        return mensagem;
    }
    
    public static String consultarAluno(String matricula) {
        String dadosAluno = "ALUNO NÃO ENCONTRADO";
        Aluno aluno = AlunoService.getAluno(matricula);
        
        if(aluno != null) {
            dadosAluno = "ALUNO CONSULTADO: \n" +
                                 "MATRÍCULA: " + aluno.getMatricula() + "\n" +
                                 "NOME: " + aluno.getNome() + "\n" + 
                                 "CPF: " + aluno.getCpf() + "\n" + 
                                 "DATA DE NASCIMENTO: " + aluno.getDataNascimento() + "\n" + 
                                 "TELEFONE: " + aluno.getTelefone() + "\n" + 
                                 "IDADE: " + aluno.getIdade();
        }
        return dadosAluno;
    }
    
    public static int quantidadeTotalDeAlunos() {
        return AlunoService.getAlunos().size();
    }
}
