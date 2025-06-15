
package com.mycompany.projeto_final.controller;

import com.mycompany.projeto_final.dao.AlunoDAO;
import com.mycompany.projeto_final.domain.Aluno;
import com.mycompany.projeto_final.domain.AlunoRequestDTO;
import com.mycompany.projeto_final.domain.AlunoResponseDTO;
import com.mycompany.projeto_final.exception.AlunoJaCadastradoException;
import com.mycompany.projeto_final.exception.AlunoNaoEncontradoException;
import com.mycompany.projeto_final.service.AlunoService;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.mycompany.projeto_final.dao.AlunoDAO;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;

public class AlunoController {
    
    public static void cadastrarAluno(AlunoRequestDTO dadosAluno) throws IllegalArgumentException, Exception {
        Aluno aluno = new Aluno(dadosAluno.nome().toUpperCase(), dadosAluno.matricula(), dadosAluno.dataNascimento(), 
                                dadosAluno.telefone(), dadosAluno.cpf());
        
        if(!AlunoService.addAluno(aluno)) {
            throw new AlunoJaCadastradoException("ERRO. ALUNO JÁ EXISTENTE");
        }
    }
    
    public static void removerAluno(String matricula) throws IllegalArgumentException, Exception {
        AlunoService.removerAluno(matricula);
    }
    
    public static AlunoResponseDTO consultarAluno(String matricula) throws AlunoNaoEncontradoException {
        Aluno aluno = AlunoService.getAluno(matricula);
        return new AlunoResponseDTO(aluno.getMatricula(), aluno.getNome(), aluno.getDataNascimento(), 
                                    aluno.getCpf(), aluno.getTelefone(), aluno.getIdade());
    }
    
    public static int quantidadeTotalDeAlunos() {
        return AlunoService.getSize();
    }
    
    public static AlunoResponseDTO maisVelho() throws Exception {
        Aluno aluno = AlunoService.verificarAlunoMaisVelho(); 
       
        return new AlunoResponseDTO(aluno.getMatricula(), aluno.getNome(),
                                    aluno.getDataNascimento(), aluno.getCpf(),
                                    aluno.getTelefone(), aluno.getIdade());
    }
   
    public static AlunoResponseDTO maisNovo() throws Exception{
        Aluno aluno = AlunoService.verificarAlunoMaisNovo();
        
        return new AlunoResponseDTO(aluno.getMatricula(), aluno.getNome(),
                aluno.getDataNascimento(), aluno.getCpf(), aluno.getTelefone(),
                aluno.getIdade());
    }
    
    public static void cadastrarAlunoNaPosicao  (AlunoRequestDTO dadosAluno,int posicao) throws IllegalArgumentException, AlunoJaCadastradoException, Exception {
        Aluno aluno = new Aluno(dadosAluno.nome().toUpperCase(), dadosAluno.matricula(), dadosAluno.dataNascimento(), 
                                dadosAluno.telefone(), dadosAluno.cpf());
        
        if(!AlunoService.addAlunoNaPosicao(aluno,posicao)) {
            throw new AlunoJaCadastradoException("ERRO. ALUNO JÁ EXISTENTE");
        }
    }
    
    public static void popularTabelaAlunos(JTable tabelaAlunos) throws Exception {
        List<Aluno> listaDeAlunos = AlunoDAO.getAllAlunos();
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("Matrícula");
        model.addColumn("Nome");
        model.addColumn("Data de Nascimento");
        model.addColumn("CPF");
        model.addColumn("Telefone");
        model.addColumn("Idade");

        DateTimeFormatter dTF = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (listaDeAlunos != null) {
            for (Aluno aluno : listaDeAlunos) {
                model.addRow(new Object[]{
                    aluno.getMatricula(),
                    aluno.getNome(),
                    aluno.getDataNascimento().format(dTF),
                    aluno.getCpf(),
                    aluno.getTelefone(),
                    aluno.getIdade()
                });
            }
        }
        tabelaAlunos.setModel(model);
    }
    
    public static void carregaremSVC() throws Exception{
        AlunoService.carregaremSVC();
    }
    
}
