/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projeto_final.dao;

import com.mycompany.projeto_final.domain.Aluno;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

/**
 *
 * @author moise
 */
public class AlunoDAO {
    public static void adicionarAluno(Aluno aluno) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("meuPU");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.persist(aluno);
        em.getTransaction().commit();
        em.close();
        emf.close();
    }
}
