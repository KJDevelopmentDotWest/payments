package com.epam.jwd.service.validator.impl;

import com.epam.jwd.service.dto.userdto.AccountDTO;
import com.epam.jwd.service.dto.userdto.UserDTO;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;

import java.util.Objects;

public class UserValidator implements Validator<UserDTO, Integer> {

    private static final Integer LOGIN_MIN_LENGTH = 3;
    private static final Integer PASSWORD_MIN_LENGTH = 5;
    private static final Integer NAME_MIN_LENGTH = 2;
    private static final Integer SURNAME_MIN_LENGTH = 2;
    private static final Integer MAX_ROLES = 2;

    private static final String LOGIN_MIN_LENGTH_EXCEPTION = "login length is less then" + LOGIN_MIN_LENGTH;
    private static final String PASSWORD_MIN_LENGTH_EXCEPTION = "password length is less then" + PASSWORD_MIN_LENGTH;
    private static final String NAME_MIN_LENGTH_EXCEPTION = "name length is less then" + NAME_MIN_LENGTH;
    private static final String SURNAME_MIN_LENGTH_EXCEPTION = "surname length is less then" + SURNAME_MIN_LENGTH;
    private static final String USER_IS_NULL_EXCEPTION = "user cannot be null";
    private static final String ACCOUNT_IS_NULL_EXCEPTION = "account cannot be null";
    private static final String ROLE_ID_WRONG_EXCEPTION = "role id wrong";

    @Override
    public void validate(UserDTO value) throws ServiceException {
        if (Objects.isNull(value)) {
            throw new ServiceException(USER_IS_NULL_EXCEPTION);
        }
        validateLogin(value.getLogin());
        validatePassword(value.getPassword());
        validateAccount(value.getAccount());
    }

    @Override
    public void validateIdNotNull(UserDTO value) throws ServiceException {
        if (Objects.isNull(value)
                || Objects.isNull(value.getAccount().getId())){
            throw new ServiceException(ID_IS_NULL_EXCEPTION);
        }
    }

    private void validateLogin(String login) throws ServiceException {
        if (Objects.isNull(login)
                || login.length() < LOGIN_MIN_LENGTH) {
            throw new ServiceException(LOGIN_MIN_LENGTH_EXCEPTION);
        }
    }

    private void validatePassword(String password) throws ServiceException {
        if (Objects.isNull(password)
                || password.length() < PASSWORD_MIN_LENGTH){
            throw new ServiceException(PASSWORD_MIN_LENGTH_EXCEPTION);
        }
    }

    private void validateAccount(AccountDTO accountDTO) throws ServiceException {
        if (Objects.isNull(accountDTO)){
            throw new ServiceException(ACCOUNT_IS_NULL_EXCEPTION);
        }
        validateName(accountDTO.getName());
        validateSurname(accountDTO.getSurname());
        validateRoleId(accountDTO.getRoleId());
    }

    private void validateName(String name) throws ServiceException {
        if (Objects.isNull(name)
                || name.length() < NAME_MIN_LENGTH) {
            throw new ServiceException(NAME_MIN_LENGTH_EXCEPTION);
        }
    }

    private void validateSurname(String surname) throws ServiceException {
        if (Objects.isNull(surname)
                || surname.length() < SURNAME_MIN_LENGTH) {
            throw new ServiceException(SURNAME_MIN_LENGTH_EXCEPTION);
        }
    }

    private void validateRoleId(Integer id) throws ServiceException {
        if (Objects.isNull(id)
                || id > MAX_ROLES) {
            throw new ServiceException(ROLE_ID_WRONG_EXCEPTION);
        }
    }
}
