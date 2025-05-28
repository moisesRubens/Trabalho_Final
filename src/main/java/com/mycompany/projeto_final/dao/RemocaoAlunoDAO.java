package com.mycompany.projeto_final.dao;

import com.mycompany.projeto_final.domain.Aluno;
import java.util.List;

public class RemocaoAlunoDAO implements AlunoDAO {

    @Override
    public List removerAluno(List alunos, Aluno a) {
        try {
            
        } catch(RuntimeException e) {
            e.printStackTrace();
        } finally { 
            return alunos;
        }
    }
    
}
