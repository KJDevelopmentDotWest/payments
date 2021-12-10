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

    /**
     *
     * @param login login iof user
     * @param password password of user
     * @param accountId id of account
     * @param isActive is user active
     * @param role role of user
     */
    public UserDto(String login, String password, Integer accountId, Boolean isActive, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.accountId = accountId;
        this.isActive = isActive;
    }

    /**
     *
     * @param id id of user
     * @param login login iof user
     * @param password password of user
     * @param accountId id of account
     * @param isActive is user active
     * @param role role of user
     */
    public UserDto(Integer id, String login, String password, Integer accountId, Boolean isActive, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.accountId = accountId;
        this.isActive = isActive;
        this.id = id;
    }

    /**
     *
     * @return role of user
     */
    public Role getRole() {
        return role;
    }

    /**
     *
     * @param role role to be set
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     *
     * @return id of account
     */
    public Integer getAccountId() {
        return accountId;
    }

    /**
     *
     * @param accountId id of account to be set
     */
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    /**
     *
     * @return true if user active, false otherwise
     */
    public Boolean getActive() {
        return isActive;
    }

    /**
     *
     * @param active true to unblock user, false to block user
     */
    public void setActive(Boolean active) {
        isActive = active;
    }

    /**
     *
     * @return login of user
     */
    public String getLogin() {
        return login;
    }

    /**
     *
     * @param login login to be set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     *
     * @return password to be set
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password password to be set
     */
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
        return "UserDto{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", accountId=" + accountId +
                ", isActive=" + isActive +
                '}';
    }
}
