/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projeto_final.dao;

import com.mycompany.projeto_final.domain.Aluno;
import com.mycompany.projeto_final.exception.AlunoNaoEncontradoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author moise
 */
public class AlunoDAO {
    static final String CSV_FILE_NAME = "ListagemAlunos.txt";
    static final DateTimeFormatter CSV_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    public static void salvarAlunosEmCSV(Aluno aluno) {
        try (BufferedWriter arquivo = new BufferedWriter(new FileWriter(CSV_FILE_NAME, true))) {
            String linha = String.join(", ",aluno.getMatricula(),aluno.getNome(),aluno.getDataNascimento().format(CSV_DATE_FORMATTER),aluno.getCpf(),aluno.getTelefone()
            ,String.valueOf(aluno.getIdade()));
            arquivo.write(linha);
            arquivo.newLine();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void adicionarAluno(Aluno aluno) throws Exception {
      
        try{
           EntityManagerFactory emf = Persistence.createEntityManagerFactory("meuPU");
           EntityManager em = emf.createEntityManager();
       
        em.getTransaction().begin();
        em.persist(aluno);
        em.getTransaction().commit();
        salvarAlunosEmCSV(aluno);
        em.close();
           emf.close();
       } catch (PersistenceException e) {
           throw new Exception("Não foi possivel conectar ao banco de dados ou os dados estão duplicados");
    } 
    }
    
    public static List<Aluno> getAllAlunos() throws AlunoNaoEncontradoException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("meuPU");
        EntityManager em = emf.createEntityManager();
        try {
            List<Aluno> alunos = em.createQuery("SELECT a FROM Aluno a", Aluno.class).getResultList();
            if (alunos.isEmpty()) {
                throw new AlunoNaoEncontradoException("MATRICULA INEXISTENTE");
            }
            return alunos;
        } catch(Exception e) {
            throw new AlunoNaoEncontradoException("MATRICULA INEXISTENTE");
        } finally {
            em.close();
            emf.close();
        }
    }
    
    public static Aluno getAluno(String matricula) throws AlunoNaoEncontradoException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("meuPU");
        EntityManager em = emf.createEntityManager();
        try {
            Aluno aluno = em.createQuery("SELECT a FROM Aluno a WHERE a.matricula = :matricula", Aluno.class)
                            .setParameter("matricula", matricula)
                            .getSingleResult();
            return aluno;
        } catch(Exception e) {
            throw new AlunoNaoEncontradoException("MATRICULA INEXISTENTE");
        } finally {
            em.close();
            emf.close();
        }
    }
}