package com.epam.jwd.repository.model.creditcard;

import com.epam.jwd.repository.model.Entity;

public class BankAccount extends Entity<Integer> {
    private Integer balance;
    private Boolean blocked;

    public BankAccount(){}

    public BankAccount(Integer balance, Boolean blocked) {
        this.balance = balance;
        this.blocked = blocked;
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
}
