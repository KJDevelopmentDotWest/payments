package com.epam.jwd.service.dto.creditcarddto;

import com.epam.jwd.dao.model.creditcard.BankAccount;
import com.epam.jwd.service.dto.EntityDTO;

import java.util.Date;
import java.util.Objects;

public class CreditCardDTO extends EntityDTO<Integer> {
    private BankAccount bankAccount;
    private String name;
    private Date expireDate;
    private Integer accountId;

    public CreditCardDTO(BankAccount bankAccount, String name, Date expireDate, Integer accountId) {
        this.bankAccount = bankAccount;
        this.name = name;
        this.expireDate = expireDate;
        this.accountId = accountId;
    }

    public CreditCardDTO(Integer id, BankAccount bankAccount, String name, Date expireDate, Integer accountId) {
        this.bankAccount = bankAccount;
        this.name = name;
        this.expireDate = expireDate;
        this.accountId = accountId;
        this.id = id;
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

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCardDTO creditCardDTO = (CreditCardDTO) o;
        return Objects.equals(bankAccount, creditCardDTO.bankAccount)
                && Objects.equals(name, creditCardDTO.name)
                && Objects.equals(expireDate, creditCardDTO.expireDate)
                && Objects.equals(accountId, creditCardDTO.accountId)
                && Objects.equals(id, creditCardDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(bankAccount, name, expireDate, accountId, id);
    }

    @Override
    public String toString() {
        return "CreditCardDTO{" +
                "id=" + id +
                ", bankAccount=" + bankAccount +
                ", name='" + name + '\'' +
                ", expireDate=" + expireDate +
                ", accountId=" + accountId +
                '}';
    }
}
