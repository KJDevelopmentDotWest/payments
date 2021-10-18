package com.epam.jwd.repository.model.creditcard;

import com.epam.jwd.repository.model.Entity;

public class BankAccount extends Entity<Integer> {
    private Integer balance;
    private Boolean blocked;

    public BankAccount(){}

    public BankAccount(Integer id, Integer balance, Boolean blocked) {
        this.balance = balance;
        this.blocked = blocked;
        this.id = id;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
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
