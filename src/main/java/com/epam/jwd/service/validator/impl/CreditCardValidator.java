package com.epam.jwd.service.validator.impl;

import com.epam.jwd.dao.model.creditcard.CreditCard;
import com.epam.jwd.service.dto.creditcarddto.BankAccountDto;
import com.epam.jwd.service.dto.creditcarddto.CreditCardDto;
import com.epam.jwd.service.exception.ExceptionCode;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;

import java.util.Date;
import java.util.Objects;

public class CreditCardValidator implements Validator<CreditCardDto, Integer> {

    private static final Integer NAME_MIN_LENGTH = 2;
    private static final Integer NAME_MAX_LENGTH = 15;

    private static final String NAME_PATTERN = "^[a-zA-Z\\s]*$";
    private static final Integer NUMBER_LENGTH = 16;

    @Override
    public void validate(CreditCardDto value, Boolean checkId) throws ServiceException {
        if (Objects.isNull(value)){
            throw new ServiceException(ExceptionCode.CREDIT_CARD_IS_NULL_EXCEPTION_CODE);
        }
        if (checkId){
            validateId(value.getId());
        }
        validateBankAccount(value.getBankAccount(), checkId);
        validateName(value.getName());
        validateDate(value.getExpireDate());
        validateUserId(value.getUserId());
        validateNumber(value.getCardNumber());
    }

    private void validateId(Integer id) throws ServiceException {
        if (Objects.isNull(id)){
            throw new ServiceException(ExceptionCode.CREDIT_CARD_ID_IS_NULL_EXCEPTION_CODE);
        }
    }

    private void validateName(String name) throws ServiceException {
        if (Objects.isNull(name)
                || name.length() < NAME_MIN_LENGTH){
            throw new ServiceException(ExceptionCode.CREDIT_CARD_NAME_TOO_SHORT_EXCEPTION_CODE);
        }
        if (name.length() > NAME_MAX_LENGTH){
            throw new ServiceException(ExceptionCode.CREDIT_CARD_NAME_TOO_LONG_EXCEPTION_CODE);
        }
        if (!name.matches(NAME_PATTERN)){
            throw new ServiceException(ExceptionCode.CREDIT_CARD_NAME_CONTAINS_FORBIDDEN_CHARACTERS_EXCEPTION_CODE);
        }
    }

    private void validateDate(Date date) throws ServiceException{
        if (Objects.isNull(date)){
            throw new ServiceException(ExceptionCode.CREDIT_CARD_DATE_IS_NULL_EXCEPTION_CODE);
        }
    }

    public void validateUserId(Integer id) throws ServiceException{
        if (Objects.isNull(id)){
            throw new ServiceException(ExceptionCode.CREDIT_CARD_USER_ID_IS_NULL_EXCEPTION_CODE);
        }
    }

    private void validateBankAccount(BankAccountDto bankAccountDTO, Boolean checkId) throws ServiceException {
        if (Objects.isNull(bankAccountDTO)){
            throw new ServiceException(ExceptionCode.BANK_ACCOUNT_IS_NULL_EXCEPTION_CODE);
        }
        if (checkId){
            validateId(bankAccountDTO.getId());
        }
        validateBalance(bankAccountDTO.getBalance());
        validateBlocked(bankAccountDTO.getBlocked());
    }

    private void validateBalance(Long balance) throws ServiceException {
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

    private void validateNumber(Long number) throws ServiceException {
        if (number.toString().length() != NUMBER_LENGTH || number < 0){
            throw new ServiceException(ExceptionCode.CREDIT_CARD_NUMBER_IS_ILLEGAL_EXCEPTION_CODE);
        }
    }
}
