package com.epam.jwd.dao.model.creditcard;

import com.epam.jwd.dao.model.Entity;

import java.util.Objects;

public class BankAccount extends Entity<Integer> {
    private Long balance;
    private Boolean blocked;

    public BankAccount(){}

    public BankAccount(Long balance, Boolean blocked) {
        this.balance = balance;
        this.blocked = blocked;
    }

    public BankAccount(Integer id, Long balance, Boolean blocked) {
        this.balance = balance;
        this.blocked = blocked;
        this.id = id;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Boolean getBlocked() {
        return blocked;
    }

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
