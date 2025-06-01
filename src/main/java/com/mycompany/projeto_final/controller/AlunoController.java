
package com.mycompany.projeto_final.controller;

import com.mycompany.projeto_final.domain.Aluno;
import com.mycompany.projeto_final.domain.AlunoRequestDTO;
import com.mycompany.projeto_final.domain.AlunoResponseDTO;
import com.mycompany.projeto_final.exception.AlunoJaCadastradoException;
import com.mycompany.projeto_final.exception.AlunoNaoEncontradoException;
import com.mycompany.projeto_final.service.AlunoService;

public class AlunoController {
    
    public static void cadastrarAluno(AlunoRequestDTO dadosAluno) throws IllegalArgumentException, AlunoJaCadastradoException {
        Aluno aluno = new Aluno(dadosAluno.nome().toUpperCase(), dadosAluno.matricula(), dadosAluno.dataNascimento(), 
                                dadosAluno.telefone(), dadosAluno.cpf());
        
        if(!AlunoService.addAluno(aluno)) {
            throw new AlunoJaCadastradoException("ERRO. ALUNO JÁ EXISTENTE");
        }
    }
    
    public static AlunoResponseDTO consultarAluno(String matricula) throws AlunoNaoEncontradoException {
        Aluno aluno = AlunoService.getAluno(matricula);
        
        if(aluno == null) {
            throw new AlunoNaoEncontradoException("MATRÍCULA NAO EXISTENTE");
        }
        return new AlunoResponseDTO(aluno.getMatricula(), aluno.getNome(), aluno.getDataNascimento(), 
                                    aluno.getCpf(), aluno.getTelefone(), aluno.getIdade());
    }
    
    public static int quantidadeTotalDeAlunos() {
        return AlunoService.getAlunos().size();
    }
    
   public static Aluno maisVelho() {
       
       return AlunoService.verificarAlunoMaisVelho();
       
   }
   public static Aluno maisNovo() {
       
       return AlunoService.verificarAlunoMaisNovo();
       
   }
}
