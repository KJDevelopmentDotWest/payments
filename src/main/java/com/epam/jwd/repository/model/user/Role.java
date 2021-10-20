package com.epam.jwd.repository.model.user;

import com.epam.jwd.repository.model.Entity;

public class Role extends Entity<Integer> {
    private String name;

    public Role() {}

    public Role(String name) {
        this.name = name;
    }

    public Role(Integer id, String name) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
