package com.epam.jwd.service.validator.impl;

import com.epam.jwd.service.dto.creditcarddto.BankAccountDTO;
import com.epam.jwd.service.dto.creditcarddto.CreditCardDTO;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;

import java.util.Date;
import java.util.Objects;

public class CreditCardValidator implements Validator<CreditCardDTO, Integer> {

    private static final Integer NAME_MIN_LENGTH = 2;

    private static final String CREDIT_CARD_IS_NULL_EXCEPTION = "credit card cannot be null";
    private static final String BANK_ACCOUNT_IS_NULL_EXCEPTION = "bank account cannot be null";
    private static final String NAME_MIN_LENGTH_EXCEPTION = "name length is less then" + NAME_MIN_LENGTH;
    private static final String DATE_IS_NULL_EXCEPTION = "id cannot be null" + NAME_MIN_LENGTH;
    private static final String BANK_ACCOUNT_BALANCE_IS_NULL_EXCEPTION = "bank account balance cannot be null";
    private static final String BANK_ACCOUNT_BLOCKED_IS_NULL_EXCEPTION = "bank account blocked state cannot be null";
    private static final String BANK_ACCOUNT_BALANCE_IS_NEGATIVE_EXCEPTION = "bank account balance cannot be negative";

    @Override
    public void validate(CreditCardDTO value) throws ServiceException {
        if (Objects.isNull(value)){
            throw new ServiceException(CREDIT_CARD_IS_NULL_EXCEPTION);
        }
        validateBankAccount(value.getBankAccount());
        validateName(value.getName());
        validateDate(value.getExpireDate());
        validateAccountId(value.getAccountId());
    }

    @Override
    public void validateIdNotNull(CreditCardDTO value) throws ServiceException {
        if (Objects.isNull(value.getId())
                || Objects.isNull(value.getBankAccount().getId())){
            throw new ServiceException(ID_IS_NULL_EXCEPTION);
        }
    }

    private void validateName(String name) throws ServiceException {
        if (Objects.isNull(name)
                || name.length() < NAME_MIN_LENGTH){
            throw new ServiceException(NAME_MIN_LENGTH_EXCEPTION);
        }
    }

    private void validateDate(Date date) throws ServiceException{
        if (Objects.isNull(date)){
            throw new ServiceException(DATE_IS_NULL_EXCEPTION);
        }
    }

    private void validateAccountId(Integer id) throws ServiceException{
        if (Objects.isNull(id)){
            throw new ServiceException(ID_IS_NULL_EXCEPTION);
        }
    }

    private void validateBankAccount(BankAccountDTO bankAccountDTO) throws ServiceException {
        if (Objects.isNull(bankAccountDTO)){
            throw new ServiceException(BANK_ACCOUNT_IS_NULL_EXCEPTION);
        }
        validateBalance(bankAccountDTO.getBalance());
        validateBlocked(bankAccountDTO.getBlocked());
    }

    private void validateBalance(Integer balance) throws ServiceException {
        if (Objects.isNull(balance)){
            throw new ServiceException(BANK_ACCOUNT_BALANCE_IS_NULL_EXCEPTION);
        }
        if (balance < 0){
            throw new ServiceException(BANK_ACCOUNT_BALANCE_IS_NEGATIVE_EXCEPTION);
        }
    }

    private void validateBlocked(Boolean blocked) throws ServiceException {
        if (Objects.isNull(blocked)){
            throw new ServiceException(BANK_ACCOUNT_BLOCKED_IS_NULL_EXCEPTION);
        }
    }
}
