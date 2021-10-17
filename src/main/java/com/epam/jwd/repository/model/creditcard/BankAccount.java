package com.epam.jwd.repository.model.creditcard;

import com.epam.jwd.repository.model.Entity;

public class BankAccount extends Entity<Integer> {
    private Double balance;
    private Boolean blocked;

    public BankAccount(Integer id){
        super(id);
    }

    public BankAccount(Integer id, Double balance, Boolean blocked) {
        super(id);
        this.balance = balance;
        this.blocked = blocked;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }
}
