package com.epam.jwd.service.dto.userdto;

import com.epam.jwd.service.dto.AbstractDTO;

import java.util.Objects;

public class AccountDTO extends AbstractDTO<Integer> {
    private String name;
    private String surname;
    private Integer roleId;

    public AccountDTO(String name, String surname, Integer roleId) {
        this.roleId = roleId;
        this.name = name;
        this.surname = surname;
    }

    public AccountDTO(Integer id, String name, String surname, Integer roleId) {
        this.roleId = roleId;
        this.name = name;
        this.surname = surname;
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDTO accountDTO = (AccountDTO) o;
        return Objects.equals(roleId, accountDTO.getRoleId())
                && Objects.equals(name, accountDTO.getName())
                && Objects.equals(surname, accountDTO.getSurname())
                && Objects.equals(id, accountDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, name, surname, id);
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "id=" + id +
                ", roleId=" + roleId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
