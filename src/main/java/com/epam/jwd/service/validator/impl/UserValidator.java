package com.epam.jwd.service.validator.impl;

import com.epam.jwd.dao.model.user.Role;
import com.epam.jwd.dao.model.user.User;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.exception.ExceptionCode;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;

import java.util.Objects;
import java.util.regex.Pattern;

public class UserValidator implements Validator<UserDto, Integer> {

    private static final Integer LOGIN_MIN_LENGTH = 3;
    private static final Integer LOGIN_MAX_LENGTH = 15;
    private static final Integer PASSWORD_MIN_LENGTH = 5;
    private static final Integer PASSWORD_MAX_LENGTH = 15;

    private static final String ONLY_ENG_AND_NUM_PATTERN = "^[a-zA-Z0-9]*$";

    @Override
    public void validate(UserDto value, Boolean checkId) throws ServiceException {
        if (Objects.isNull(value)) {
            throw new ServiceException(ExceptionCode.USER_IS_NULL_EXCEPTION_CODE);
        }
        if (checkId){
            validateId(value.getId());
        }
        validateLogin(value.getLogin());
        validatePassword(value.getPassword());
        validateRole(value.getRole());
    }

    /**
     *
     * @param login login to be validated
     * @throws ServiceException if login is null
     */
    public void validateLoginNotNull(String login) throws ServiceException{
        if (Objects.isNull(login)){
            throw new ServiceException(ExceptionCode.USER_LOGIN_IS_NULL_EXCEPTION_CODE);
        }
    }

    private void validateLogin(String login) throws ServiceException {
        if (Objects.isNull(login)
                || login.length() < LOGIN_MIN_LENGTH) {
            throw new ServiceException(ExceptionCode.USER_LOGIN_TOO_SHORT_EXCEPTION_CODE);
        }
        if (login.length() > LOGIN_MAX_LENGTH) {
            throw new ServiceException(ExceptionCode.USER_LOGIN_TOO_LONG_EXCEPTION_CODE);
        }
        if (!login.matches(ONLY_ENG_AND_NUM_PATTERN)){
            throw new ServiceException(ExceptionCode.USER_LOGIN_CONTAINS_FORBIDDEN_CHARACTERS_EXCEPTION_CODE);
        }
    }

    private void validatePassword(String password) throws ServiceException {
        if (Objects.isNull(password)
                || password.length() < PASSWORD_MIN_LENGTH){
            throw new ServiceException(ExceptionCode.USER_PASSWORD_TOO_SHORT_EXCEPTION_CODE);
        }
        if (password.length() > PASSWORD_MAX_LENGTH){
            throw new ServiceException(ExceptionCode.USER_PASSWORD_TOO_LONG_EXCEPTION_CODE);
        }
    }

    private void validateRole(Role role) throws ServiceException {
        if (Objects.isNull(role)) {
            throw new ServiceException(ExceptionCode.USER_ROLE_ID_IS_WRONG_EXCEPTION_CODE);
        }
    }

    private void validateId(Integer id) throws ServiceException {
        if (Objects.isNull(id)){
            throw new ServiceException(ExceptionCode.USER_ID_IS_NULL_EXCEPTION_CODE);
        }
    }
}
