package com.epam.jwd.dao.model.user;

import com.epam.jwd.dao.model.Entity;

import java.util.Objects;

public class User extends Entity<Integer> {

    private String login;
    private String password;
    private Account account;

    public User(){}

    public User(String login, String password, Account account) {
        this.login = login;
        this.password = password;
        this.account = account;
    }

    public User(Integer id, String login, String password, Account account) {
        this.login = login;
        this.password = password;
        this.account = account;
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(login, user.getLogin())
                && Objects.equals(password, user.getPassword())
                && Objects.equals(account, user.getAccount())
                && Objects.equals(id, user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, account, id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", account=" + account +
                '}';
    }
}
