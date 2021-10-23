package com.epam.jwd.dao.api;

import com.epam.jwd.dao.model.Entity;

import java.util.List;

public interface DAO<T extends Entity<K>, K> {
    T save(T entity);
    Boolean update(T entity);
    Boolean delete(T entity);
    List<T> findAll();
    T findById(K id);
}
