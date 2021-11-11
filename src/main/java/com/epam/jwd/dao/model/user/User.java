package com.epam.jwd.dao.model.user;

import com.epam.jwd.dao.model.Entity;

import java.util.Objects;

public class User extends Entity<Integer> {

    private String login;
    private String password;
    private Role role;
    private Integer accountId;
    private Boolean isActive;

    public User(String login, String password, Integer accountId, Boolean isActive, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.accountId = accountId;
        this.isActive = isActive;
    }

    public User(Integer id, String login, String password, Integer accountId, Boolean isActive, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.accountId = accountId;
        this.isActive = isActive;
        this.id = id;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer integer) {
        this.accountId = integer;
    }

    public void setLogin(String login){
        this.login = login;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(login, user.getLogin())
                && Objects.equals(password, user.getPassword())
                && Objects.equals(accountId, user.getAccountId())
                && Objects.equals(id, user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, accountId, id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", account=" + accountId +
                '}';
    }
}
