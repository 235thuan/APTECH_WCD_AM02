package com.t2303e.assignment02.repository.generic;

import com.t2303e.assignment02.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGenericRepository<T extends NamedEntity> implements GenericRepository<T> {
    private final Class<T> type;

    protected AbstractGenericRepository(Class<T> type) {
        this.type = type;
    }

    @Override
    public List<T> findAll() {
        List<T> entities = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            entities = session.createQuery("from " + type.getSimpleName(), type).list();
        } catch (Exception e) {
            System.out.println("Lỗi findAll: " + e.getMessage());
        }
        return entities;
    }

    @Override
    public void save(T entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Lỗi save: " + e.getMessage());
        }
    }

    @Override
    public T findById(int id) {
        T entity = null;
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            entity = session.get(type, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Lỗi findById: " + e.getMessage());
        }
        return entity;
    }

    @Override
    public void update(T entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Lỗi update: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            T entity = session.get(type, id);
            if (entity != null) {
                session.delete(entity);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Lỗi delete: " + e.getMessage());
        }
    }

    @Override
    public List<T> findByName(String name) {
        List<T> entities = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<T> query = cb.createQuery(type);
            Root<T> root = query.from(type);

            // Check if the entity has a 'name' field
            if (root.getModel().getAttributes().stream().anyMatch(a -> a.getName().equals("name"))) {
                query.select(root).where(cb.like(root.get("name"), "%" + name + "%"));
                entities = session.createQuery(query).getResultList();
            } else {
                System.out.println("Entity does not have a 'name' field.");
            }
        } catch (Exception e) {
            System.out.println("Lỗi findByName: " + e.getMessage());
        }
        return entities;
    }

}
