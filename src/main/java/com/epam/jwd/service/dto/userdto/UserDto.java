package com.epam.jwd.service.dto.userdto;

import com.epam.jwd.dao.model.user.Role;
import com.epam.jwd.service.dto.AbstractDto;

import java.util.Objects;

public class UserDto extends AbstractDto<Integer> {
    private String login;
    private String password;
    private Role role;
    private Integer accountId;
    private Boolean isActive;

    public UserDto(String login, String password, Integer accountId, Boolean isActive, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.accountId = accountId;
        this.isActive = isActive;
    }

    public UserDto(Integer id, String login, String password, Integer accountId, Boolean isActive, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.accountId = accountId;
        this.isActive = isActive;
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDTO = (UserDto) o;
        return Objects.equals(login, userDTO.getLogin())
                && Objects.equals(password, userDTO.getPassword())
                && Objects.equals(accountId, userDTO.getAccountId())
                && Objects.equals(id, userDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, accountId, id);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", account=" + accountId +
                '}';
    }
}
