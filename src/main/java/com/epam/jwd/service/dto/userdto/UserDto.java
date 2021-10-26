package com.epam.jwd.service.dto.userdto;

import com.epam.jwd.service.dto.AbstractDto;

import java.util.Objects;

public class UserDto extends AbstractDto<Integer> {
    private String login;
    private String password;
    private AccountDto account;

    public UserDto(String login, String password, AccountDto account) {
        this.login = login;
        this.password = password;
        this.account = account;
    }

    public UserDto(Integer id, String login, String password, AccountDto account) {
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

    public AccountDto getAccount() {
        return account;
    }

    public void setAccount(AccountDto account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDTO = (UserDto) o;
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
