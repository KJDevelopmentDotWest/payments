package com.epam.jwd.service.dto;

public class AbstractDto<T> {
    protected T id;

    /**
     *
     * @return id of abstract dto
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
