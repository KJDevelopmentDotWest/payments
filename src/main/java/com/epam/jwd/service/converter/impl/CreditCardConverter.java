package com.epam.jwd.service.converter.impl;

import com.epam.jwd.dao.model.creditcard.BankAccount;
import com.epam.jwd.dao.model.creditcard.CreditCard;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.dto.creditcarddto.BankAccountDTO;
import com.epam.jwd.service.dto.creditcarddto.CreditCardDTO;

public class CreditCardConverter implements Converter<CreditCard, CreditCardDTO, Integer> {
    @Override
    public CreditCard convert(CreditCardDTO value) {
        return null;
    }

    @Override
    public CreditCardDTO convert(CreditCard value) {
        return null;
    }

    private BankAccount convertBankAccount(BankAccountDTO bankAccountDTO){
        BankAccount bankAccount = new BankAccount(bankAccountDTO.getBalance(),
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
