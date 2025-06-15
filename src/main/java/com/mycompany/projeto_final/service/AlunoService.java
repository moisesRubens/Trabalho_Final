package com.mycompany.projeto_final.service;

import com.mycompany.projeto_final.dao.AlunoDAO;
import com.mycompany.projeto_final.dao.RemocaoAlunoDAO;
import com.mycompany.projeto_final.domain.Aluno;
import com.mycompany.projeto_final.exception.AlunoNaoEncontradoException;
import jakarta.persistence.PersistenceException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

public class AlunoService {
    
    public static List<Aluno> alunos = new ArrayList<>();
    
    public static int getSize() {
        return alunos.size();
    } 
    
    private static boolean existeAluno(String matricula) {
        for(Aluno aluno : alunos) {
            if(aluno.getMatricula().equals(matricula))
                return true;
        }
        return false;
    }
    
    private static boolean verificarCpf(String cpf) {
        char[] str = cpf.toCharArray();
        
        if(cpf.length() != 14) {
            return false;
        }
        for(char c : str) {
            if(Character.isLetter(c) || Character.isWhitespace(c)) {
                return false;
            }
        }
        return true;
    }
    
    private static boolean verificarIdade(int idade) {
        return(idade >= 0 && idade <= 130); 
    }
    
    private static boolean verificarNome(String nome) {
        char[] str = nome.toCharArray();
        
        if(nome.isEmpty()) {
            return false;
        }
        
        for(char c : str) {
            if(Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
    
    private static boolean verificarTelefone(String telefone) {
        char[] str = telefone.toCharArray();
        
        if(telefone.length() != 14) {
            return false;
        }
        for(char c : str) {
            if(Character.isLetter(c) || Character.isWhitespace(c)) {
                return false;
            }
        }
        return true;
    }
    
    private static boolean verificarMatricula(String matricula) {
        char[] str = matricula.toCharArray();
        
        if(matricula.isEmpty()) {
            return false;
        }
        
        for(char c : str) {
            if(!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
    
    private static int verificarDataDeNascimento(LocalDate date) {
        LocalDate now = LocalDate.now();
        
        if(date == null || date.isAfter(now)) {
            return -1;
        }
        
        int idade = Period.between(date, now).getYears();
        
        if(idade < 0 || idade > 130) {
            return -1;
        }
        return idade;
    }
    
    public static boolean addAluno(Aluno aluno) throws IllegalArgumentException, Exception {
        aluno.setIdade(verificarDataDeNascimento(aluno.getDataNascimento()));
        int idade = aluno.getIdade();
        
        if(!verificarCpf(aluno.getCpf()) || !verificarIdade(aluno.getIdade()) 
          || !verificarNome(aluno.getNome()) || !verificarTelefone(aluno.getTelefone())
          || !verificarMatricula(aluno.getMatricula()) || idade == -1) {
            throw new IllegalArgumentException("INSIRA DADOS VÁLIDOS PARA O CADASTRO");
        }
       
        if(!existeAluno(aluno.getMatricula())) {
            AlunoDAO.adicionarAluno(aluno);
            return true;
        }
        return false;
    }
    
    public static void removerAluno(String matricula) throws IllegalArgumentException, Exception {
        if(matricula.equals("MATRICULA")) {
            throw new IllegalArgumentException("INSIRA UMA MATRICULA");
        }
        
        List<Aluno> alunos = AlunoDAO.getAllAlunos();
        Aluno aluno = null;
        for(Aluno a : alunos) {
            if(a.getMatricula().equals(matricula)) {
                aluno = a;
                break;
            }
        }
        if(aluno == null) {
            throw new AlunoNaoEncontradoException("MATRICULA INEXISTENTE");
        }
        RemocaoAlunoDAO rAD = new RemocaoAlunoDAO();
        rAD.removerAluno(alunos, aluno);
        RemocaoAlunoDAO.atualizarArquivoCSV(alunos);
    }
    
    public static Aluno getAluno(String matricula) throws IllegalArgumentException, AlunoNaoEncontradoException {
        if(matricula.equals("MATRICULA")) {
            throw new IllegalArgumentException("INSIRA UMA MATRICULA");
        }
        Aluno aluno = AlunoDAO.getAluno(matricula);
        return aluno;
    } 

    public static Aluno verificarAlunoMaisVelho() throws Exception {
        List<Aluno> alunos = AlunoDAO.getAllAlunos();

        Aluno alunoVelho = alunos.get(0);
        Aluno atual;
        for (int i = 1; i < alunos.size(); i++) {
            atual = alunos.get(i);
            if (atual.getIdade() > alunoVelho.getIdade()) {
                alunoVelho = atual;
            }
        }

        return alunoVelho;
    }

    
    public static Aluno verificarAlunoMaisNovo() throws Exception {
        List<Aluno> alunos = AlunoDAO.getAllAlunos();
        
        Aluno alunoMaisNovo = alunos.get(0);
        Aluno atual;
        for(int i = 1; i<alunos.size(); i++) {
            atual = alunos.get(i);
            if (atual.getIdade() < alunoMaisNovo.getIdade()) {
                alunoMaisNovo = atual;
            }
        }
        return alunoMaisNovo;
    }
    
    public static boolean addAlunoNaPosicao(Aluno aluno,int posicao) throws IllegalArgumentException {
        aluno.setIdade(verificarDataDeNascimento(aluno.getDataNascimento()));
        int idade = aluno.getIdade();
        
        if(!verificarCpf(aluno.getCpf()) || !verificarIdade(aluno.getIdade()) 
          || !verificarNome(aluno.getNome()) || !verificarTelefone(aluno.getTelefone())
          || !verificarMatricula(aluno.getMatricula()) || idade == -1) {
            throw new IllegalArgumentException("INSIRA DADOS VÁLIDOS PARA O CADASTRO");
        }
       
        if(!existeAluno(aluno.getMatricula())) {
            alunos.add(posicao,aluno);
            
            return true;
        }
        return false;
    }
}
