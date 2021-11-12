package com.epam.jwd.dao.model.creditcard;

import com.epam.jwd.dao.model.Entity;

import java.util.Date;
import java.util.Objects;

public class CreditCard extends Entity<Integer> {
    private BankAccount bankAccount;
    private String name;
    private Date expireDate;
    private Integer userId;
    private Long cardNumber;

    public CreditCard(BankAccount bankAccount, String name, Date expireDate, Integer userId, Long cardNumber) {
        this.bankAccount = bankAccount;
        this.name = name;
        this.expireDate = expireDate;
        this.userId = userId;
        this.cardNumber = cardNumber;
    }

    public CreditCard(Integer id, BankAccount bankAccount, String name, Date expireDate, Integer userId, Long cardNumber) {
        this.bankAccount = bankAccount;
        this.name = name;
        this.expireDate = expireDate;
        this.userId = userId;
        this.cardNumber = cardNumber;
        this.id = id;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCard creditCard = (CreditCard) o;
        return Objects.equals(bankAccount, creditCard.bankAccount)
                && Objects.equals(name, creditCard.name)
                && Objects.equals(expireDate, creditCard.expireDate)
                && Objects.equals(userId, creditCard.userId)
                && Objects.equals(id, creditCard.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(bankAccount, name, expireDate, userId, id);
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "id=" + id +
                ", bankAccount=" + bankAccount +
                ", name='" + name + '\'' +
                ", expireDate=" + expireDate +
                ", accountId=" + userId +
                '}';
    }
}
