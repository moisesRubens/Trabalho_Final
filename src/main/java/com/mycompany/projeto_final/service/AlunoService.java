package com.mycompany.projeto_final.service;

import com.mycompany.projeto_final.domain.Aluno;
import java.util.ArrayList;
import java.util.List;

public class AlunoService {
    private static List<Aluno> alunos = new ArrayList<>();

    public static List<Aluno> getAlunos() {
        return alunos;
    }
    
    private static boolean existeAluno(String matricula) {
        for(Aluno aluno : alunos) {
            if(aluno.getMatricula().equals(matricula))
                return true;
        }
        return false;
    }
    
    public static boolean addAluno(Aluno aluno) {
        if(!existeAluno(aluno.getMatricula())) {
            alunos.add(aluno);
            return true;
        }
        return false;
    }
    
    public static Aluno getAluno(String matricula) {
        if(matricula == null || matricula.isEmpty()) {
            return null;
        }
        for(Aluno aluno : alunos) {
            if(aluno.getMatricula().equals(matricula)) {
                return aluno;
            }
        }
        return null;
    } 
}
