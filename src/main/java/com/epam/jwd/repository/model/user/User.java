package com.epam.jwd.repository.model.user;

import com.epam.jwd.repository.model.Entity;

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
}
