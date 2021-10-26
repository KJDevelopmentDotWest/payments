package com.epam.jwd.service.dto.creditcarddto;

import com.epam.jwd.service.dto.AbstractDTO;

import java.util.Date;
import java.util.Objects;

public class CreditCardDTO extends AbstractDTO<Integer> {
    private BankAccountDTO bankAccount;
    private String name;
    private Date expireDate;
    private Integer userId;

    public CreditCardDTO(BankAccountDTO bankAccount, String name, Date expireDate, Integer accountId) {
        this.bankAccount = bankAccount;
        this.name = name;
        this.expireDate = expireDate;
        this.userId = accountId;
    }

    public CreditCardDTO(Integer id, BankAccountDTO bankAccount, String name, Date expireDate, Integer userId) {
        this.bankAccount = bankAccount;
        this.name = name;
        this.expireDate = expireDate;
        this.userId = userId;
        this.id = id;
    }

    public BankAccountDTO getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccountDTO bankAccount) {
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCardDTO creditCardDTO = (CreditCardDTO) o;
        return Objects.equals(bankAccount, creditCardDTO.bankAccount)
                && Objects.equals(name, creditCardDTO.name)
                && Objects.equals(expireDate, creditCardDTO.expireDate)
                && Objects.equals(userId, creditCardDTO.userId)
                && Objects.equals(id, creditCardDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(bankAccount, name, expireDate, userId, id);
    }

    @Override
    public String toString() {
        return "CreditCardDTO{" +
                "id=" + id +
                ", bankAccount=" + bankAccount +
                ", name='" + name + '\'' +
                ", expireDate=" + expireDate +
                ", accountId=" + userId +
                '}';
    }
}
