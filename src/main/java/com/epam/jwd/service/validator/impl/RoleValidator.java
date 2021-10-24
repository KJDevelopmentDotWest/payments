package com.epam.jwd.service.validator.impl;

import com.epam.jwd.service.dto.userdto.RoleDTO;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;

import java.util.Objects;

public class RoleValidator implements Validator<RoleDTO, Integer> {

    private static final Integer NAME_MIN_LENGTH = 2;
    private static final String NAME_MIN_LENGTH_EXCEPTION = "name length is less then" + NAME_MIN_LENGTH;

    @Override
    public void validate(RoleDTO value) throws ServiceException {
        if (Objects.isNull(value)){
            throw new ServiceException(ID_IS_NULL_EXCEPTION);
        }
        validateName(value.getName());
    }


    private void validateName(String name) throws ServiceException {
        if (Objects.isNull(name)
                || name.length() < NAME_MIN_LENGTH) {
            throw new ServiceException(NAME_MIN_LENGTH_EXCEPTION);
        }
    }
}
