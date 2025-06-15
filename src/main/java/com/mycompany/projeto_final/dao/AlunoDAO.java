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
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("meuPU");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.persist(aluno);
        em.getTransaction().commit();
        em.close();
        emf.close();
        salvarAlunosEmCSV(aluno);
    }
    
    public static List<Aluno> getAllAlunos() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("meuPU");
        EntityManager em = emf.createEntityManager();
        try {
            List<Aluno> alunos = getAllAlunosFromDB(em);
            if(alunos.isEmpty()) {
                throw new AlunoNaoEncontradoException("NÃO HÁ ALUNOS CADASTRADOS");
            }
            return alunos;
        }catch(AlunoNaoEncontradoException e) {
            throw e;
        } catch(Exception e) {
            tratar(e);
            throw new PersistenceException("ERRO AO BUSCAR ALUNOS", e);
        } finally {
            em.close();
            emf.close();
        }
    }
    
    private static List<Aluno> getAllAlunosFromDB(EntityManager em) {
        List<Aluno> alunos = em.createQuery("SELECT a FROM Aluno a", Aluno.class).getResultList();
        return alunos;
    }
    
    private static void tratar(Exception e) {
        logger.log(Level.WARNING, "ERRO AO BUSCAR DADOS DE ALUNOS NO BANCO DE DADOS", e);
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