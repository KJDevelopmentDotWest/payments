package com.epam.jwd.service.validator.impl;

import com.epam.jwd.service.dto.userdto.AccountDto;
import com.epam.jwd.service.exception.ExceptionCode;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;

import java.util.Objects;

public class AccountValidator implements Validator<AccountDto, Integer> {

    private static final Integer NAME_MIN_LENGTH = 2;
    private static final Integer NAME_MAX_LENGTH = 15;
    private static final Integer SURNAME_MIN_LENGTH = 2;
    private static final Integer SURNAME_MAX_LENGTH = 15;

    private static final String PATTERN = "^[a-zA-Z-]*$";


    @Override
    public void validate(AccountDto value, Boolean checkId) throws ServiceException {
        if (Objects.isNull(value)){
            throw new ServiceException(ExceptionCode.ACCOUNT_IS_NULL_EXCEPTION_CODE);
        }
        if (checkId){
            validateId(value.getId());
        }
        validateName(value.getName());
        validateSurname(value.getSurname());
    }

    private void validateName(String name) throws ServiceException {
        if (Objects.isNull(name)
                || name.length() < NAME_MIN_LENGTH) {
            throw new ServiceException(ExceptionCode.ACCOUNT_NAME_TOO_SHORT_EXCEPTION_CODE);
        }
        if (name.length() > NAME_MAX_LENGTH){
            throw new ServiceException(ExceptionCode.ACCOUNT_NAME_TOO_LONG_EXCEPTION_CODE);
        }
        if (!name.matches(PATTERN)){
            throw new ServiceException(ExceptionCode.ACCOUNT_NAME_CONTAINS_FORBIDDEN_CHARACTERS_EXCEPTION_CODE);
        }
    }

    private void validateSurname(String surname) throws ServiceException {
        if (Objects.isNull(surname)
                || surname.length() < SURNAME_MIN_LENGTH) {
            throw new ServiceException(ExceptionCode.ACCOUNT_SURNAME_TOO_SHORT_EXCEPTION_CODE);
        }
        if (surname.length() > SURNAME_MAX_LENGTH){
            throw new ServiceException(ExceptionCode.ACCOUNT_SURNAME_TOO_LONG_EXCEPTION_CODE);
        }
        if (!surname.matches(PATTERN)){
            throw new ServiceException(ExceptionCode.ACCOUNT_SURNAME_CONTAINS_FORBIDDEN_CHARACTERS_EXCEPTION_CODE);
        }
    }

    private void validateId(Integer id) throws ServiceException {
        if (Objects.isNull(id)){
            throw new ServiceException(ExceptionCode.USER_ID_IS_NULL_EXCEPTION_CODE);
        }
    }
}
