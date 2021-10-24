package com.epam.jwd.service.validator.api;

import com.epam.jwd.service.dto.EntityDTO;
import com.epam.jwd.service.exception.ServiceException;

import java.util.Objects;

public interface Validator<T extends EntityDTO<K>, K> {
    String ID_IS_NULL_EXCEPTION = "id cannot be null";

    void validate(T value) throws ServiceException;

    default void validateIdNotNull(T value) throws  ServiceException{
        if (Objects.isNull(value.getId())){
            throw new ServiceException(ID_IS_NULL_EXCEPTION);
        }
    }

    default void validateIdNotNull(K value) throws ServiceException {
        if (Objects.isNull(value)){
            throw new ServiceException(ID_IS_NULL_EXCEPTION);
        }
    }
}
