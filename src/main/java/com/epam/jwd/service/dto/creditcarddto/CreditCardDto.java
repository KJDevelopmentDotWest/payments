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

    /**
     *
     * @param bankAccount bank account of this credit card
     * @param name name of credit card
     * @param expireDate expire date of credit card
     * @param userId id of owner of credit card
     * @param cardNumber number of credit card
     */
    public CreditCardDto(BankAccountDto bankAccount, String name, Date expireDate, Integer userId, Long cardNumber) {
        this.bankAccount = bankAccount;
        this.name = name;
        this.expireDate = expireDate;
        this.userId = userId;
        this.cardNumber = cardNumber;
    }

    /**
     *
     * @param id id of credit card
     * @param bankAccount bank account of this credit card
     * @param name name of credit card
     * @param expireDate expire date of credit card
     * @param userId id of credit card owner
     * @param cardNumber number of credit card
     */
    public CreditCardDto(Integer id, BankAccountDto bankAccount, String name, Date expireDate, Integer userId, Long cardNumber) {
        this.bankAccount = bankAccount;
        this.name = name;
        this.expireDate = expireDate;
        this.userId = userId;
        this.cardNumber = cardNumber;
        this.id = id;
    }

    /**
     *
     * @return credit card number
     */
    public Long getCardNumber() {
        return cardNumber;
    }

    /**
     *
     * @param cardNumber number to be set
     */
    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     *
     * @return bank account of credit card
     */
    public BankAccountDto getBankAccount() {
        return bankAccount;
    }

    /**
     *
     * @param bankAccount bank account to be set
     */
    public void setBankAccount(BankAccountDto bankAccount) {
        this.bankAccount = bankAccount;
    }

    /**
     *
     * @return name of credit card
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name name to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return expire date of credit card
     */
    public Date getExpireDate() {
        return expireDate;
    }

    /**
     *
     * @param expireDate expire date to be set
     */
    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    /**
     *
     * @return id of credit card owner
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     *
     * @param userId owner id to be set
     */
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
