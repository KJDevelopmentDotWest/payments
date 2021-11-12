package com.epam.jwd.service.validator.impl;

import com.epam.jwd.service.dto.paymentdto.PaymentDto;
import com.epam.jwd.service.exception.ExceptionCode;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;

import java.util.Date;
import java.util.Objects;

public class PaymentValidator implements Validator<PaymentDto, Integer> {

    private static final Integer NAME_MIN_LENGTH = 2;

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
        if (Objects.isNull(address)){
            throw new ServiceException(ExceptionCode.PAYMENT_ADDRESS_IS_NULL_EXCEPTION_CODE);
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
    }

}
