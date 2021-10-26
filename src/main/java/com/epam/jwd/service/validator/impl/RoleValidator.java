package com.epam.jwd.service.validator.impl;

import com.epam.jwd.service.dto.userdto.RoleDto;
import com.epam.jwd.service.exception.ExceptionCode;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;

import java.util.Objects;

public class RoleValidator implements Validator<RoleDto, Integer> {

    private static final Integer NAME_MIN_LENGTH = 2;

    @Override
    public void validate(RoleDto value, Boolean checkId) throws ServiceException {
        if (Objects.isNull(value)){
            throw new ServiceException(ExceptionCode.ROLE_IS_NULL_EXCEPTION_CODE);
        }
        if (checkId){
            validateId(value.getId());
        }
        validateName(value.getName());
    }

    private void validateId(Integer id) throws ServiceException {
        if(Objects.isNull(id)){
            throw new ServiceException(ExceptionCode.ROLE_ID_IS_NULL_EXCEPTION_CODE);
        }
    }

    private void validateName(String name) throws ServiceException {
        if (Objects.isNull(name)
                || name.length() < NAME_MIN_LENGTH) {
            throw new ServiceException(ExceptionCode.ROLE_NAME_IS_TOO_SHORT_EXCEPTION_CODE);
        }
    }
}
