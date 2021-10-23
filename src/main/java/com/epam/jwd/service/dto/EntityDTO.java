package com.epam.jwd.service.dto;

public class EntityDTO<T> {
    protected T id;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }
}
