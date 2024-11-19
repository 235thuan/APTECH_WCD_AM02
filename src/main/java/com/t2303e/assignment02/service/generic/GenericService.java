package com.t2303e.assignment02.service.generic;

import java.util.List;

public interface GenericService<T> {
    List<T> getAll();
    T getById(int id) throws Exception;
    void save(T entity) throws Exception;
    void update(T entity) throws Exception;
    void delete(int id) throws Exception;
    List<T> search(String keyword) throws Exception;
}

