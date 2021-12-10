package com.epam.jwd.dao.model;

/**
 * This class represent entity
 * @param <T> type of id
 */
public class Entity<T> {
    protected T id;

    public Entity() {}

    /**
     *
     * @return id of entity
     */
    public T getId() {
        return id;
    }

    /**
     *
     * @param id id to be set
     */
    public void setId(T id) {
        this.id = id;
    }
}
