package com.t2303e.assignment02.service.generic;

import com.t2303e.assignment02.repository.generic.GenericRepository;
import com.t2303e.assignment02.repository.generic.NamedEntity;

import java.util.List;

public abstract class AbstractGenericService<T extends NamedEntity> implements GenericService<T> {
    protected final GenericRepository<T> repository;

    public AbstractGenericService(GenericRepository<T> repository) {
        this.repository = repository;
    }

    @Override
    public List<T> getAll() {
        return repository.findAll();
    }

    @Override
    public T getById(int id) throws Exception {
        T entity = repository.findById(id);
        if (entity == null) {
            throw new Exception("Đối tượng không tồn tại với ID: " + id);
        }
        return entity;
    }

    @Override
    public void save(T entity) throws Exception {
        if (entity.getName() == null || entity.getName().trim().isEmpty()) {
            throw new Exception("Tên không được để trống!");
        }
        if (!repository.findByName(entity.getName()).isEmpty()) {
            throw new Exception("Tên đã tồn tại!");
        }
        repository.save(entity);
    }

    @Override
    public void update(T entity) throws Exception {
        if (entity == null) {
            throw new Exception("Đối tượng không được để trống!"); // Entity must not be null
        }
        if (entity.getId() <= 0) {
            throw new Exception("ID của đối tượng không hợp lệ!"); // ID must be a positive number
        }
        if (entity.getName() == null || entity.getName().trim().isEmpty()) {
            throw new Exception("Tên không được để trống!"); // Name must not be empty
        }
        repository.update(entity);
    }


    @Override
    public void delete(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("ID không hợp lệ!"); // ID must be a positive number
        }
        T entity = repository.findById(id);
        if (entity == null) {
            throw new Exception("Không tìm thấy đối tượng với ID: " + id); // Entity must exist
        }
        repository.delete(id);
    }

    @Override
    public List<T> search(String keyword) throws Exception {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new Exception("Từ khóa tìm kiếm không được để trống!"); // Keyword must not be null or empty
        }
        if (keyword.length() < 3) {
            throw new Exception("Từ khóa tìm kiếm phải có ít nhất 3 ký tự!"); // Keyword must be at least 3 characters long
        }
        return repository.findByName(keyword);
    }
}

