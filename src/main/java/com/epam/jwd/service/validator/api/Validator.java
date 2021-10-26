package com.epam.jwd.service.validator.api;

import com.epam.jwd.service.dto.AbstractDTO;
import com.epam.jwd.service.exception.ExceptionCode;
import com.epam.jwd.service.exception.ServiceException;

import java.util.Objects;

public interface Validator<T extends AbstractDTO<K>, K> {

    void validate(T value, Boolean checkId) throws ServiceException;
    default void validateIdNotNull(K value) throws ServiceException {
        if (Objects.isNull(value)){
            throw new ServiceException(ExceptionCode.ID_IS_NULL_EXCEPTION_CODE);
        }
    }
}
