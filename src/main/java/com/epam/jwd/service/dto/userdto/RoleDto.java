package com.epam.jwd.service.dto.userdto;

import com.epam.jwd.service.dto.AbstractDto;

import java.util.Objects;

public class RoleDto extends AbstractDto<Integer> {
    private String name;

    public RoleDto(String name) {
        this.name = name;
    }

    public RoleDto(Integer id, String name) {
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
        RoleDto roleDTO = (RoleDto) o;
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
