package com.epam.jwd.repository.model.user;

import com.epam.jwd.repository.model.Entity;

import java.util.ArrayList;
import java.util.List;

public class Account extends Entity<Integer> {
    private String name;
    private String surname;
    private Integer roleId;

    public Account() {}

    public Account(String name, String surname, Integer roleId) {
        this.name = name;
        this.surname = surname;
        this.roleId = roleId;
    }

    public Account(Integer id, String name, String surname, Integer roleId) {
        this.name = name;
        this.surname = surname;
        this.roleId = roleId;
        this.id = id;
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

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", roleId=" + roleId +
                '}';
    }
}
