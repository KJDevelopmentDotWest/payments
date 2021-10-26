package com.epam.jwd.service.dto.userdto;

import com.epam.jwd.service.dto.AbstractDTO;

import java.util.Objects;

public class RoleDTO extends AbstractDTO<Integer> {
    private String name;

    public RoleDTO(String name) {
        this.name = name;
    }

    public RoleDTO(Integer id, String name) {
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
        RoleDTO roleDTO = (RoleDTO) o;
        return Objects.equals(name, roleDTO.getName())
                && Objects.equals(id, roleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }

    @Override
    public String toString() {
        return "RoleDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
