package com.mycompany.projeto_final.service;

import com.mycompany.projeto_final.domain.Aluno;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
    
    private static boolean verificarCpf(String cpf) {
        char[] str = cpf.toCharArray();
        
        if(cpf.length() != 14) {
            return false;
        }
        for(char c : str) {
            if(Character.isLetter(c)) {
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
            if(Character.isLetter(c)) {
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
    
    public static boolean addAluno(Aluno aluno) throws IllegalArgumentException {
        aluno.setIdade(verificarDataDeNascimento(aluno.getDataNascimento()));
        int idade = aluno.getIdade();
        
        if(!verificarCpf(aluno.getCpf()) || !verificarIdade(aluno.getIdade()) 
          || !verificarNome(aluno.getNome()) || !verificarTelefone(aluno.getTelefone())
          || idade == -1) {
            throw new IllegalArgumentException("INSIRA DADOS VÁLIDOS PARA O CADASTRO");
        }
       
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

    public static Aluno verificarAlunoMaisVelho(){
         
        if(alunos.isEmpty()) {
             return null;
         }
        Aluno alunoVelho = alunos.getFirst();
        for(int i =1;i <alunos.size();i++) {
            Aluno atual = alunos.get(i);
            
            if(atual.getIdade() >alunoVelho.getIdade()) {
                alunoVelho = atual;
            }
        }
            
        return alunoVelho;
    }
    public static Aluno verificarAlunoMaisNovo() {
         
        if(alunos.isEmpty()) {
             return null;
         }
        Aluno alunoNovo = alunos.getFirst();
        for(int i =1;i <alunos.size();i++) {
            Aluno atual = alunos.get(i);
            
            if(atual.getIdade() < alunoNovo.getIdade()) {
                alunoNovo = atual;
            }
        }
            
        return alunoNovo;
    }
        
        
 
    
}
