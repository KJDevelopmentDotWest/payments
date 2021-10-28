package com.epam.jwd.service.validator.impl;

import com.epam.jwd.dao.model.user.Role;
import com.epam.jwd.dao.model.user.User;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.exception.ExceptionCode;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;

import java.util.Objects;

public class UserValidator implements Validator<UserDto, Integer> {

    private static final Integer LOGIN_MIN_LENGTH = 3;
    private static final Integer PASSWORD_MIN_LENGTH = 5;

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

    public void validateLoginNotNull(String login) throws ServiceException{
        if (Objects.isNull(login)){
            throw new ServiceException(ExceptionCode.USER_LOGIN_IS_NULL_EXCEPTION_CODE);
        }
    }

    public void validateLoginUnique(User user) throws ServiceException{
        if (!Objects.isNull(user)){
            throw new ServiceException(ExceptionCode.USER_LOGIN_IS_NOT_UNIQUE_EXCEPTION_CODE);
        }
    }

    private void validateLogin(String login) throws ServiceException {
        if (Objects.isNull(login)
                || login.length() < LOGIN_MIN_LENGTH) {
            throw new ServiceException(ExceptionCode.USER_LOGIN_TOO_SHORT_EXCEPTION_CODE);
        }
    }

    private void validatePassword(String password) throws ServiceException {
        if (Objects.isNull(password)
                || password.length() < PASSWORD_MIN_LENGTH){
            throw new ServiceException(ExceptionCode.USER_PASSWORD_TOO_SHORT_EXCEPTION_CODE);
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
