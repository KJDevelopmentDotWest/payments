package com.epam.jwd.repository.api;

import com.epam.jwd.repository.model.Entity;

import java.util.List;

public interface Repository<T extends Entity<K>, K> {
    Boolean save(T entity);
    Boolean update(T entity);
    Boolean delete(T entity);
    List<T> findAll();
    T findById(K id);
}
