package com.epam.jwd.service.validator.impl;

import com.epam.jwd.service.dto.paymentdto.PaymentDTO;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;

import java.util.Date;
import java.util.Objects;

public class PaymentValidator implements Validator<PaymentDTO, Integer> {

    private static final Integer NAME_MIN_LENGTH = 2;

    private static final String PAYMENT_IS_NULL_EXCEPTION = "payment cannot be null";
    private static final String PAYMENT_ADDRESS_IS_NULL_EXCEPTION = "payment address cannot be null";
    private static final String PAYMENT_PRICE_IS_NULL_EXCEPTION = "payment price cannot be null";
    private static final String PAYMENT_PRICE_IS_NEGATIVE_EXCEPTION = "payment price cannot be negative";
    private static final String PAYMENT_COMMITTED_IS_NULL_EXCEPTION = "payment committed cannot be null";
    private static final String PAYMENT_DATE_IS_NULL_EXCEPTION = "payment date cannot be null";
    private static final String NAME_MIN_LENGTH_EXCEPTION = "name length is less then" + NAME_MIN_LENGTH;

    @Override
    public void validate(PaymentDTO value) throws ServiceException {
        if (Objects.isNull(value)){
            throw new ServiceException(PAYMENT_IS_NULL_EXCEPTION);
        }
        validateUserId(value.getUserId());
        validateAddress(value.getDestinationAddress());
        validatePrice(value.getPrice());
        validateCommitted(value.getCommitted());
        validateTime(value.getTime());
        validateName(value.getName());
    }

    private void validateUserId(Integer id) throws ServiceException {
        if (Objects.isNull(id)){
            throw new ServiceException(ID_IS_NULL_EXCEPTION);
        }
    }

    private void validateAddress(String address) throws ServiceException {
        if (Objects.isNull(address)){
            throw new ServiceException(PAYMENT_ADDRESS_IS_NULL_EXCEPTION);
        }
    }

    private void validatePrice(Integer price) throws ServiceException {
        if (Objects.isNull(price)){
            throw new ServiceException(PAYMENT_PRICE_IS_NULL_EXCEPTION);
        }
        if (price < 0){
            throw new ServiceException(PAYMENT_PRICE_IS_NEGATIVE_EXCEPTION);
        }
    }

    private void validateCommitted(Boolean committed) throws ServiceException {
        if (Objects.isNull(committed)){
            throw new ServiceException(PAYMENT_COMMITTED_IS_NULL_EXCEPTION);
        }
    }

    private void validateTime(Date time) throws ServiceException {
        if (Objects.isNull(time)){
            throw new ServiceException(PAYMENT_DATE_IS_NULL_EXCEPTION);
        }
    }

    private void validateName(String name) throws ServiceException {
        if (Objects.isNull(name)
                || name.length() < NAME_MIN_LENGTH) {
            throw new ServiceException(NAME_MIN_LENGTH_EXCEPTION);
        }
    }

}
