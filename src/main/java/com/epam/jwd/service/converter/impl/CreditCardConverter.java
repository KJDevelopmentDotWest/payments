package com.epam.jwd.service.converter.impl;

import com.epam.jwd.dao.model.creditcard.BankAccount;
import com.epam.jwd.dao.model.creditcard.CreditCard;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.dto.creditcarddto.BankAccountDto;
import com.epam.jwd.service.dto.creditcarddto.CreditCardDto;

public class CreditCardConverter implements Converter<CreditCard, CreditCardDto, Integer> {
    @Override
    public CreditCard convert(CreditCardDto value) {
        CreditCard creditCard = new CreditCard(value.getId(),
                convertBankAccount(value.getBankAccount()),
                value.getName(),
                value.getExpireDate(),
                value.getUserId(),
                value.getCardNumber());
        return creditCard;
    }

    @Override
    public CreditCardDto convert(CreditCard value) {
        CreditCardDto creditCardDTO = new CreditCardDto(value.getId(),
                convertBankAccount(value.getBankAccount()),
                value.getName(),
                value.getExpireDate(),
                value.getUserId(),
                value.getCardNumber());
        return creditCardDTO;
    }

    private BankAccount convertBankAccount(BankAccountDto bankAccountDTO){
        BankAccount bankAccount = new BankAccount(bankAccountDTO.getId(),
                bankAccountDTO.getBalance(),
                bankAccountDTO.getBlocked());
        return bankAccount;
    }

    private BankAccountDto convertBankAccount(BankAccount bankAccount){
        BankAccountDto bankAccountDTO = new BankAccountDto(bankAccount.getId(),
                bankAccount.getBalance(),
                bankAccount.getBlocked());
        return bankAccountDTO;
    }
}
