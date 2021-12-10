package com.epam.jwd.service.validator.api;

import com.epam.jwd.service.dto.AbstractDto;
import com.epam.jwd.service.exception.ExceptionCode;
import com.epam.jwd.service.exception.ServiceException;

import java.util.Objects;

/**
 *
 * @param <T> abstract dto that converter will operate
 * @param <K> type of id
 */
public interface Validator<T extends AbstractDto<K>, K> {

    /**
     *
     * @param value value to be validated
     * @param checkId true if id must be validated, false otherwise
     * @throws ServiceException if validation fails
     */
    void validate(T value, Boolean checkId) throws ServiceException;

    /**
     *
     * @param value id to be validated
     * @throws ServiceException if value is null
     */
    default void validateIdNotNull(K value) throws ServiceException {
        if (Objects.isNull(value)){
            throw new ServiceException(ExceptionCode.ID_IS_NULL_EXCEPTION_CODE);
        }
    }
}
