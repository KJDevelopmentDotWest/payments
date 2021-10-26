package com.epam.jwd.service.validator.impl;

import com.epam.jwd.service.dto.creditcarddto.BankAccountDTO;
import com.epam.jwd.service.dto.creditcarddto.CreditCardDTO;
import com.epam.jwd.service.exception.ExceptionCode;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;

import java.util.Date;
import java.util.Objects;

public class CreditCardValidator implements Validator<CreditCardDTO, Integer> {

    private static final Integer NAME_MIN_LENGTH = 2;

    @Override
    public void validate(CreditCardDTO value, Boolean checkId) throws ServiceException {
        if (Objects.isNull(value)){
            throw new ServiceException(ExceptionCode.CREDIT_CARD_IS_NULL_EXCEPTION_CODE);
        }
        if (checkId){
            validateId(value.getId());
        }
        validateBankAccount(value.getBankAccount(), checkId);
        validateName(value.getName());
        validateDate(value.getExpireDate());
        validateAccountId(value.getUserId());
    }

    private void validateId(Integer id) throws ServiceException {
        if (Objects.isNull(id)){
            throw new ServiceException(ExceptionCode.CREDIT_CARD_ID_IS_NULL_EXCEPTION_CODE);
        }
    }

    private void validateName(String name) throws ServiceException {
        if (Objects.isNull(name)
                || name.length() < NAME_MIN_LENGTH){
            throw new ServiceException(ExceptionCode.USER_LOGIN_TOO_SHORT_EXCEPTION_CODE);
        }
    }

    private void validateDate(Date date) throws ServiceException{
        if (Objects.isNull(date)){
            throw new ServiceException(ExceptionCode.CREDIT_CARD_DATE_IS_NULL_EXCEPTION_CODE);
        }
    }

    private void validateAccountId(Integer id) throws ServiceException{
        if (Objects.isNull(id)){
            throw new ServiceException(ExceptionCode.CREDIT_CARD_ID_IS_NULL_EXCEPTION_CODE);
        }
    }

    private void validateBankAccount(BankAccountDTO bankAccountDTO, Boolean checkId) throws ServiceException {
        if (Objects.isNull(bankAccountDTO)){
            throw new ServiceException(ExceptionCode.BANK_ACCOUNT_IS_NULL_EXCEPTION_CODE);
        }
        if (checkId){
            validateId(bankAccountDTO.getId());
        }
        validateBalance(bankAccountDTO.getBalance());
        validateBlocked(bankAccountDTO.getBlocked());
    }

    private void validateBalance(Integer balance) throws ServiceException {
        if (Objects.isNull(balance)){
            throw new ServiceException(ExceptionCode.BANK_ACCOUNT_BALANCE_IS_NULL_EXCEPTION_CODE);
        }
        if (balance < 0){
            throw new ServiceException(ExceptionCode.BANK_ACCOUNT_BALANCE_IS_NEGATIVE_EXCEPTION_CODE);
        }
    }

    private void validateBlocked(Boolean blocked) throws ServiceException {
        if (Objects.isNull(blocked)){
            throw new ServiceException(ExceptionCode.BANK_ACCOUNT_BLOCKED_IS_NULL_EXCEPTION_CODE);
        }
    }
}
