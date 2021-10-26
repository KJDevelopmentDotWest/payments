package com.epam.jwd.service.dto.userdto;

import com.epam.jwd.service.dto.AbstractDTO;

import java.util.Objects;

public class UserDTO extends AbstractDTO<Integer> {
    private String login;
    private String password;
    private AccountDTO account;

    public UserDTO(String login, String password, AccountDTO account) {
        this.login = login;
        this.password = password;
        this.account = account;
    }

    public UserDTO(Integer id, String login, String password, AccountDTO account) {
        this.login = login;
        this.password = password;
        this.account = account;
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountDTO getAccount() {
        return account;
    }

    public void setAccount(AccountDTO account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(login, userDTO.getLogin())
                && Objects.equals(password, userDTO.getPassword())
                && Objects.equals(account, userDTO.getAccount())
                && Objects.equals(id, userDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, account, id);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", account=" + account +
                '}';
    }
}
