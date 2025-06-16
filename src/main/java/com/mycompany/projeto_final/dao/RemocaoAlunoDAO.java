package com.mycompany.projeto_final.dao;

import com.mycompany.projeto_final.domain.Aluno;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class RemocaoAlunoDAO implements AlunoDAOInterface {
    @Override
    public List<Aluno> removerAluno(List alunos, Aluno a) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("meuPU");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            Aluno alunoRemover = em.find(Aluno.class, a.getMatricula());

            if (alunoRemover == null) {
                em.getTransaction().rollback();
                return alunos;
            }

            em.remove(alunoRemover);
            em.getTransaction().commit();

            alunos.removeIf(aluno -> ((Aluno)aluno).getMatricula().equals(a.getMatricula()));
            return alunos;

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (em != null) em.close();
            if (emf != null) emf.close();
        }
    }
    
    public static void atualizarArquivoCSV(List<Aluno> alunos) {
    try (BufferedWriter arquivo = new BufferedWriter(new FileWriter(AlunoDAO.CSV_FILE_NAME, false))) {
        for (Aluno aluno : alunos) {
            String linha = String.join(", ",
                aluno.getMatricula(),
                aluno.getNome(),
                aluno.getDataNascimento().format(AlunoDAO.CSV_DATE_FORMATTER),
                aluno.getCpf(),
                aluno.getTelefone(),
                String.valueOf(aluno.getIdade())
            );
            arquivo.write(linha);
            arquivo.newLine();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

}
