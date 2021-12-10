package com.epam.jwd.dao.model.user;

import com.epam.jwd.dao.model.Entity;

import java.util.Objects;

/**
 * This class represents user
 */
public class User extends Entity<Integer> {

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
    public User(String login, String password, Integer accountId, Boolean isActive, Role role) {
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
    public User(Integer id, String login, String password, Integer accountId, Boolean isActive, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.accountId = accountId;
        this.isActive = isActive;
        this.id = id;
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
     * @param login login to be set
     */
    public void setLogin(String login){
        this.login = login;
    }

    /**
     *
     * @param password password to be set
     */
    public void setPassword(String password){
        this.password = password;
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
     * @return password to be set
     */
    public String getPassword() {
        return password;
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
