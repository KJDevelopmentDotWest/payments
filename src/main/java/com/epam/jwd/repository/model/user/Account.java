package com.epam.jwd.repository.model.user;

import com.epam.jwd.repository.model.Entity;

import java.util.ArrayList;
import java.util.List;

public class Account extends Entity<Integer> {
    private String name;
    private String surname;
    private List<Integer> cardsId = new ArrayList<>();
    private Integer roleId;

    public Account() {}

    public Account(String name, String surname, List<Integer> cardsId, Integer roleId) {
        this.name = name;
        this.surname = surname;
        this.cardsId = cardsId;
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

    public List<Integer> getCardsId() {
        return cardsId;
    }

    public void setCardsId(List<Integer> cardsId) {
        this.cardsId = cardsId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
