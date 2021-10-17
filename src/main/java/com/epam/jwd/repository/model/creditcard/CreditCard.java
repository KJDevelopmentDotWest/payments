package com.epam.jwd.repository.model.creditcard;

import com.epam.jwd.repository.model.Entity;

import java.util.Date;

public class CreditCard extends Entity<Integer> {
    private BankAccount bankAccount;
    private String name;
    private Date expireDate;
    private Integer accountId;

    public CreditCard() {}

    public CreditCard(BankAccount bankAccount, String name, Date expireDate, Integer accountId) {
        this.bankAccount = bankAccount;
        this.name = name;
        this.expireDate = expireDate;
        this.accountId = accountId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
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
