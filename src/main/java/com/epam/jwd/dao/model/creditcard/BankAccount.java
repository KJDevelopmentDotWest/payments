package com.epam.jwd.dao.model.creditcard;

import com.epam.jwd.dao.model.Entity;

import java.util.Objects;

/**
 * This class represents bank account
 */

public class BankAccount extends Entity<Integer> {
    private Long balance;
    private Boolean blocked;

    /**
     * @param balance initial balance of bank account
     * @param blocked is balance blocked
     */
    public BankAccount(Long balance, Boolean blocked) {
        this.balance = balance;
        this.blocked = blocked;
    }

    /**
     * @param id id of bank account
     * @param balance initial balance of bank account
     * @param blocked is balance blocked
     */
    public BankAccount(Integer id, Long balance, Boolean blocked) {
        this.balance = balance;
        this.blocked = blocked;
        this.id = id;
    }

    /**
     *
     * @return balance of bank account
     */
    public Long getBalance() {
        return balance;
    }

    /**
     *
     * @param balance to be set
     */
    public void setBalance(Long balance) {
        this.balance = balance;
    }

    /**
     *
     * @return true if bank account is blocked, false otherwise
     */
    public Boolean getBlocked() {
        return blocked;
    }

    /**
     *
     * @param blocked true to block bank account, false to unblock
     */
    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccount bankAccount = (BankAccount) o;
        return Objects.equals(balance, bankAccount.getBalance())
                && Objects.equals(blocked, bankAccount.getBlocked())
                && Objects.equals(id, bankAccount.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(balance, blocked, id);
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", balance=" + balance +
                ", blocked=" + blocked +
                '}';
    }
}
