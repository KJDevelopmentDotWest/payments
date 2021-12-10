package com.epam.jwd.dao.model.user;

import com.epam.jwd.dao.model.Entity;

import java.util.Objects;

/**
 * this class represents account
 */
public class Account extends Entity<Integer> {
    private String name;
    private String surname;
    private Integer profilePictureId;

    /**
     *
     * @param name name of account owner
     * @param surname surname of account owner
     * @param profilePictureId id of profile picture
     */
    public Account(String name, String surname, Integer profilePictureId) {
        this.name = name;
        this.surname = surname;
        this.profilePictureId = profilePictureId;
    }

    /**
     *
     * @param id id of account
     * @param name name of account owner
     * @param surname surname of account owner
     * @param profilePictureId id of profile picture
     */
    public Account(Integer id, String name, String surname, Integer profilePictureId) {
        this.name = name;
        this.surname = surname;
        this.profilePictureId = profilePictureId;
        this.id = id;
    }

    /**
     *
     * @return id pf profile picture
     */
    public Integer getProfilePictureId() {
        return profilePictureId;
    }

    /**
     *
     * @param profilePictureId id of profile picture to be set
     */
    public void setProfilePictureId(Integer profilePictureId) {
        this.profilePictureId = profilePictureId;
    }

    /**
     *
     * @return name of account owner
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name name to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return surname of account owner
     */
    public String getSurname() {
        return surname;
    }

    /**
     *
     * @param surname surname to be set
     */
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
