/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projeto_final.dao;

import com.mycompany.projeto_final.domain.Aluno;
import com.mycompany.projeto_final.exception.AlunoNaoEncontradoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author moise
 */
public class AlunoDAO {
    static final String CSV_FILE_NAME = "ListagemAlunos.txt";
    static final DateTimeFormatter CSV_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final Logger logger = Logger.getLogger(AlunoDAO.class.getName());
    
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
           EntityManagerFactory emf = null;
           EntityManager em = null;
        try{
            emf = Persistence.createEntityManagerFactory("meuPU");
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(aluno);
            em.getTransaction().commit();
            salvarAlunosEmCSV(aluno);
       } catch (PersistenceException e) {
           throw new Exception("Não foi possivel conectar ao banco de dados ou os dados estão duplicados");
    } finally {
           if (em != null) {
            em.close();
        }
           if (emf != null) {
            emf.close();
        }
    }
    }
    
    public static List<Aluno> getAllAlunos() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("meuPU");
        EntityManager em = emf.createEntityManager();
        try {
            return getAllAlunosAux(em);
        }catch(AlunoNaoEncontradoException e) {
            tratar(e);
            throw e;
        } catch(Exception e) {
            tratar(e);
            throw new PersistenceException("ERRO AO BUSCAR ALUNOS", e);
        } finally {
            em.close();
            emf.close();
        }
    }
    
    private static List<Aluno> getAllAlunosAux(EntityManager em) throws AlunoNaoEncontradoException {
        List<Aluno> alunos = em.createQuery("SELECT a FROM Aluno a", Aluno.class).getResultList();
        if(alunos.isEmpty()) {
            throw new AlunoNaoEncontradoException("NÃO HÁ ALUNOS CADASTRADOS");
        }
        return alunos;
    }
    
    private static void tratar(Exception e) {
        logger.log(Level.WARNING, "ERRO AO BUSCAR DADOS", e);
    }

    public static Aluno getAluno(String matricula) throws AlunoNaoEncontradoException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("meuPU");
        EntityManager em = emf.createEntityManager();
        try {
            return getAlunoAux(em, matricula);
        } catch(NoResultException e) {
            tratar(e);
            throw new AlunoNaoEncontradoException("MATRICULA INEXISTENTE");
        }catch(Exception e) {
            tratar(e);
            throw new PersistenceException("ERRO AO BUSCAR ALUNO");
        } finally {
            em.close();
            emf.close();
        }
    }
    
    private static Aluno getAlunoAux(EntityManager em, String matricula) throws Exception{
        Aluno aluno = em.createQuery("SELECT a FROM Aluno a WHERE a.matricula = :matricula", Aluno.class)
                .setParameter("matricula", matricula)
                .getSingleResult();
        return aluno;
    }
    
     public static long getTotalAlunos() {
          EntityManagerFactory emf = Persistence.createEntityManagerFactory("meuPU");  
           EntityManager em = emf.createEntityManager();
         try {
             return em.createQuery("SELECT COUNT(a) FROM Aluno a", Long.class).getSingleResult();
         } finally {
             em.close();
             emf.close();
         }
     }
}