package com.epam.jwd.service.validator.impl;

import com.epam.jwd.service.dto.paymentdto.PaymentDto;
import com.epam.jwd.service.exception.ExceptionCode;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;

import java.util.Date;
import java.util.Objects;

public class PaymentValidator implements Validator<PaymentDto, Integer> {

    private static final Integer NAME_MIN_LENGTH = 2;
    private static final Integer NAME_MAX_LENGTH = 15;
    private static final Integer ADDRESS_MIN_LENGTH = 2;
    private static final Integer ADDRESS_MAX_LENGTH = 15;
    private static final String NAME_PATTERN = "^[a-zA-Z\\s]*$";
    private static final String ADDRESS_PATTERN = "^[a-zA-Z-/.@]*$";

    @Override
    public void validate(PaymentDto value, Boolean checkId) throws ServiceException {
        if (Objects.isNull(value)){
            throw new ServiceException(ExceptionCode.PAYMENT_IS_NULL_EXCEPTION_CODE);
        }
        if (checkId){
            validateId(value.getId());
        }
        validateUserId(value.getUserId());
        validateAddress(value.getDestinationAddress());
        validatePrice(value.getPrice());
        validateCommitted(value.getCommitted());
        validateName(value.getName());
    }

    private void validateId(Integer id) throws ServiceException{
        if (Objects.isNull(id)) {
            throw new ServiceException(ExceptionCode.PAYMENT_ID_IS_NULL_EXCEPTION_CODE);
        }
    }

    public void validateUserId(Integer id) throws ServiceException {
        if (Objects.isNull(id)){
            throw new ServiceException(ExceptionCode.PAYMENT_USER_ID_IS_NULL_EXCEPTION_CODE);
        }
    }

    private void validateAddress(String address) throws ServiceException {
        if (Objects.isNull(address) || address.length() < ADDRESS_MIN_LENGTH){
            throw new ServiceException(ExceptionCode.PAYMENT_ADDRESS_TOO_SHORT_EXCEPTION_CODE);
        }
        if (address.length() > ADDRESS_MAX_LENGTH){
            throw new ServiceException(ExceptionCode.PAYMENT_ADDRESS_TOO_LONG_EXCEPTION_CODE);
        }
        if (!address.matches(ADDRESS_PATTERN)){
            throw new ServiceException(ExceptionCode.PAYMENT_ADDRESS_CONTAINS_FORBIDDEN_CHARACTERS_EXCEPTION_CODE);
        }
    }

    private void validatePrice(Long price) throws ServiceException {
        if (Objects.isNull(price)){
            throw new ServiceException(ExceptionCode.PAYMENT_PRICE_IS_NULL_EXCEPTION_CODE);
        }
        if (price < 0){
            throw new ServiceException(ExceptionCode.PAYMENT_PRICE_IS_NEGATIVE_EXCEPTION_CODE);
        }
    }

    private void validateCommitted(Boolean committed) throws ServiceException {
        if (Objects.isNull(committed)){
            throw new ServiceException(ExceptionCode.PAYMENT_COMMITTED_IS_NULL_EXCEPTION_CODE);
        }
    }

    private void validateName(String name) throws ServiceException {
        if (Objects.isNull(name)
                || name.length() < NAME_MIN_LENGTH) {
            throw new ServiceException(ExceptionCode.PAYMENT_NAME_TOO_SHORT_EXCEPTION_CODE);
        }
        if (name.length() > NAME_MAX_LENGTH){
            throw new ServiceException(ExceptionCode.PAYMENT_NAME_TOO_LONG_EXCEPTION_CODE);
        }
        if (!name.matches(NAME_PATTERN)){
            throw new ServiceException(ExceptionCode.PAYMENT_NAME_CONTAINS_FORBIDDEN_CHARACTERS_EXCEPTION_CODE);
        }
    }

}
