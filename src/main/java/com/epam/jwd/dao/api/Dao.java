package com.epam.jwd.dao.api;

import com.epam.jwd.dao.model.Entity;

import java.util.List;

/**
 *
 * @param <T> entity that class will operate
 * @param <K> id type of entity that class will operate
 */
public interface Dao<T extends Entity<K>, K> {
    /**
     *
     * @param entity entity to be saved
     * @return saved entity
     */
    T save(T entity);

    /**
     *
     * @param entity entity to be updated
     * @return true if entity updated successfully, false otherwise
     */
    Boolean update(T entity);

    /**
     *
     * @param entity entity to be deleted
     * @return true if entity deleted successfully, false otherwise
     */
    Boolean delete(T entity);

    /**
     *
     * @return list of entities
     */
    List<T> findAll();

    /**
     *
     * @param id entity id
     * @return entity with id == entity.id
     */
    T findById(K id);

    /**
     *
     * @return number of entities in database
     */
    Integer getRowsNumber();
}
