package com.epam.jwd.service.converter.impl;

import com.epam.jwd.dao.model.creditcard.BankAccount;
import com.epam.jwd.dao.model.creditcard.CreditCard;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.dto.creditcarddto.BankAccountDTO;
import com.epam.jwd.service.dto.creditcarddto.CreditCardDTO;

public class CreditCardConverter implements Converter<CreditCard, CreditCardDTO, Integer> {
    @Override
    public CreditCard convert(CreditCardDTO value) {
        CreditCard creditCard = new CreditCard(value.getId(),
                convertBankAccount(value.getBankAccount()),
                value.getName(),
                value.getExpireDate(),
                value.getUserId());
        return creditCard;
    }

    @Override
    public CreditCardDTO convert(CreditCard value) {
        CreditCardDTO creditCardDTO = new CreditCardDTO(value.getId(),
                convertBankAccount(value.getBankAccount()),
                value.getName(),
                value.getExpireDate(),
                value.getUserId());
        return creditCardDTO;
    }

    private BankAccount convertBankAccount(BankAccountDTO bankAccountDTO){
        BankAccount bankAccount = new BankAccount(bankAccountDTO.getId(),
                bankAccountDTO.getBalance(),
                bankAccountDTO.getBlocked());
        return bankAccount;
    }

    private BankAccountDTO convertBankAccount(BankAccount bankAccount){
        BankAccountDTO bankAccountDTO = new BankAccountDTO(bankAccount.getId(),
                bankAccount.getBalance(),
                bankAccount.getBlocked());
        return bankAccountDTO;
    }
}
