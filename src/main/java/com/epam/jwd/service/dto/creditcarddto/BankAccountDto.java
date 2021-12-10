package com.epam.jwd.service.dto.creditcarddto;

import com.epam.jwd.service.dto.AbstractDto;

import java.util.Objects;

public class BankAccountDto extends AbstractDto<Integer> {
    private Long balance;
    private Boolean blocked;

    /**
     * @param balance initial balance of bank account
     * @param blocked is balance blocked
     */
    public BankAccountDto(Long balance, Boolean blocked) {
        this.balance = balance;
        this.blocked = blocked;
    }

    /**
     * @param id id of bank account
     * @param balance initial balance of bank account
     * @param blocked is balance blocked
     */
    public BankAccountDto(Integer id, Long balance, Boolean blocked) {
        this.balance = balance;
        this.blocked = blocked;
        this.id = id;
    }

    /**
     *
     * @return balance of bank account
     */
    public Long getBalance() {
        return balance;
    }

    /**
     *
     * @param balance to be set
     */
    public void setBalance(Long balance) {
        this.balance = balance;
    }

    /**
     *
     * @return true if bank account is blocked, false otherwise
     */
    public Boolean getBlocked() {
        return blocked;
    }

    /**
     *
     * @param blocked true to block bank account, false to unblock
     */
    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccountDto bankAccountDTO = (BankAccountDto) o;
        return Objects.equals(balance, bankAccountDTO.getBalance())
                && Objects.equals(blocked, bankAccountDTO.getBlocked())
                && Objects.equals(id, bankAccountDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(balance, blocked, id);
    }

    @Override
    public String toString() {
        return "BankAccountDTO{" +
                "id=" + id +
                ", balance=" + balance +
                ", blocked=" + blocked +
                '}';
    }
}
