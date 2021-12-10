package com.epam.jwd.service.dto.userdto;

import com.epam.jwd.service.dto.AbstractDto;

import java.util.Objects;

public class AccountDto extends AbstractDto<Integer> {
    private String name;
    private String surname;
    private Integer profilePictureId;

    /**
     *
     * @param name name of account owner
     * @param surname surname of account owner
     * @param profilePictureId id of profile picture
     */
    public AccountDto(String name, String surname, Integer profilePictureId) {
        this.profilePictureId = profilePictureId;
        this.name = name;
        this.surname = surname;
    }

    /**
     *
     * @param id id of account
     * @param name name of account owner
     * @param surname surname of account owner
     * @param profilePictureId id of profile picture
     */
    public AccountDto(Integer id, String name, String surname, Integer profilePictureId) {
        this.profilePictureId = profilePictureId;
        this.name = name;
        this.surname = surname;
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
        AccountDto accountDTO = (AccountDto) o;
        return Objects.equals(profilePictureId, accountDTO.getProfilePictureId())
                && Objects.equals(name, accountDTO.getName())
                && Objects.equals(surname, accountDTO.getSurname())
                && Objects.equals(id, accountDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(profilePictureId, name, surname, id);
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "id=" + id +
                ", roleId=" + profilePictureId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
