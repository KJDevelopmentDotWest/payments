package com.epam.jwd.dao.model.user;

import com.epam.jwd.dao.model.Entity;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(name, role.getName())
                && Objects.equals(id, role.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
