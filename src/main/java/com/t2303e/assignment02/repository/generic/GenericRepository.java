package com.t2303e.assignment02.repository.generic;

import java.util.List;

public interface GenericRepository<T extends NamedEntity> {
    List<T> findAll();
    void save(T entity);
    T findById(int id);
    void update(T entity);
    void delete(int id);
    List<T> findByName(String name);
}

