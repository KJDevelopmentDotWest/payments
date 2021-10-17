package com.epam.jwd.repository.model;

public class Entity<T> {
    protected T id;

    public Entity(T id) {
        this.id = id;
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }
}
