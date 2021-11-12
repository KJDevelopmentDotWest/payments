package com.epam.jwd.service.dto.creditcarddto;

import com.epam.jwd.service.dto.AbstractDto;

import java.util.Date;
import java.util.Objects;

public class CreditCardDto extends AbstractDto<Integer> {
    private BankAccountDto bankAccount;
    private String name;
    private Date expireDate;
    private Integer userId;
    private Long cardNumber;

    public CreditCardDto(BankAccountDto bankAccount, String name, Date expireDate, Integer accountId, Long cardNumber) {
        this.bankAccount = bankAccount;
        this.name = name;
        this.expireDate = expireDate;
        this.userId = accountId;
        this.cardNumber = cardNumber;
    }

    public CreditCardDto(Integer id, BankAccountDto bankAccount, String name, Date expireDate, Integer userId, Long cardNumber) {
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

    public BankAccountDto getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccountDto bankAccount) {
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
        CreditCardDto creditCardDTO = (CreditCardDto) o;
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
