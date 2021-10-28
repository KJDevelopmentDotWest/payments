package com.epam.jwd.dao.model.user;

import com.epam.jwd.dao.model.Entity;

import java.util.Objects;

public class Account extends Entity<Integer> {
    private String name;
    private String surname;
    private Integer profilePictureId;

    public Account() {}

    public Account(String name, String surname, Integer profilePictureId) {
        this.name = name;
        this.surname = surname;
        this.profilePictureId = profilePictureId;
    }

    public Account(Integer id, String name, String surname, Integer profilePictureId) {
        this.name = name;
        this.surname = surname;
        this.profilePictureId = profilePictureId;
        this.id = id;
    }

    public Integer getProfilePictureId() {
        return profilePictureId;
    }

    public void setProfilePictureId(Integer profilePictureId) {
        this.profilePictureId = profilePictureId;
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
        Account account = (Account) o;
        return Objects.equals(name, account.getName())
                && Objects.equals(surname, account.getSurname())
                && Objects.equals(id, account.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname,id);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
