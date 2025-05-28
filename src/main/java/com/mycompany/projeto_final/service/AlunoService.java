package com.mycompany.projeto_final.service;

import com.mycompany.projeto_final.domain.Aluno;
import java.util.ArrayList;
import java.util.List;

public class AlunoService {
    static List<Aluno> alunos = new ArrayList<>();
    
    private static boolean existeAluno(String matricula) {
        for(Aluno aluno : alunos) {
            if(aluno.getMatricula().equals(matricula))
                return true;
        }
        return false;
    }
    
    public static boolean cadastrarAluno(Aluno aluno) {
        if(!existeAluno(aluno.getMatricula())) {
            alunos.add(aluno);
            return true;
        }
        return false;
    }
    
    public static String getAluno(String matricula) {
        String dadosAluno = "ALUNO NÃO ENCONTRADO";
           
        if(!matricula.isEmpty()) {
            for(Aluno aluno : alunos) {
                if(aluno.getMatricula().equals(matricula)) {
                    dadosAluno = "ALUNO CONSULTADO: \n" +
                                 "MATRÍCULA: " + aluno.getMatricula() + "\n" +
                                 "NOME: " + aluno.getNome() + "\n" + 
                                 "CPF: " + aluno.getCpf() + "\n" + 
                                 "DATA DE NASCIMENTO: " + aluno.getDataNascimento() + "\n" + 
                                 "TELEFONE: " + aluno.getTelefone() + "\n" + 
                                 "IDADE: " + aluno.getIdade();
                }
            }
        }
        return dadosAluno;
    } 
    
    public static int getTotalAlunos() {
        return 0;
    }
}
