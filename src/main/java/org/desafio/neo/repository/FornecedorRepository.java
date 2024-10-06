package org.desafio.neo.repository;

import org.desafio.neo.model.Fornecedor;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class FornecedorRepository {
    private Session session;

    public FornecedorRepository(Session session) {
        this.session = session;
    }

    public void save(Fornecedor fornecedor) {
        Transaction transaction = session.beginTransaction();
        try {
            session.save(fornecedor);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Erro ao salvar Fornecedor", e);
        }
    }

    public Fornecedor findById(Long id) {
        return session.get(Fornecedor.class, id);
    }

    public List<Fornecedor> findAll() {
        return session.createQuery("from Fornecedor", Fornecedor.class).list();
    }

    public void update(Fornecedor fornecedor) {
        Transaction transaction = session.beginTransaction();
        try {
            session.update(fornecedor);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Erro ao salvar Fornecedor", e);
        }
    }

    public void delete(Fornecedor fornecedor) {
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(fornecedor);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Erro ao salvar Fornecedor", e);
        }
    }
}