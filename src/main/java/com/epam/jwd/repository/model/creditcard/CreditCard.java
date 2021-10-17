package com.epam.jwd.repository.model.creditcard;

import com.epam.jwd.repository.model.Entity;

import java.util.Date;

public class CreditCard extends Entity<Integer> {
    private BankAccount bankAccount;
    private String name;
    private Date expireDate;

    public CreditCard(Integer id) {
        super(id);
    }

    public CreditCard(Integer id, BankAccount bankAccount, String name, Date expireDate) {
        super(id);
        this.bankAccount = bankAccount;
        this.name = name;
        this.expireDate = expireDate;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }
}
