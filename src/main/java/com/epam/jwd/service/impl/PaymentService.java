package com.epam.jwd.service.impl;

import com.epam.jwd.dao.api.DAO;
import com.epam.jwd.dao.impl.PaymentDAO;
import com.epam.jwd.dao.model.payment.Payment;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.converter.impl.PaymentConverter;
import com.epam.jwd.service.dto.paymentdto.PaymentDTO;
import com.epam.jwd.service.exception.EntityNotFoundException;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;
import com.epam.jwd.service.validator.impl.PaymentValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PaymentService implements Service<PaymentDTO, Integer> {

    private final DAO<Payment, Integer> dao = new PaymentDAO();
    private final Validator<PaymentDTO, Integer> validator = new PaymentValidator();
    private final Converter<Payment, PaymentDTO, Integer> converter = new PaymentConverter();

    private static final String THERE_IS_NO_SUCH_PAYMENT_EXCEPTION = "there is no payment with provided id";

    @Override
    public PaymentDTO create(PaymentDTO value) throws ServiceException {
        validator.validate(value);
        return converter.convert(dao.save(converter.convert(value)));
    }

    @Override
    public Boolean update(PaymentDTO value) throws ServiceException {
        validator.validate(value);
        validator.validateIdNotNull(value);
        return dao.update(converter.convert(value));
    }

    @Override
    public Boolean delete(PaymentDTO value) throws ServiceException {
        validator.validate(value);
        validator.validateIdNotNull(value);
        return dao.delete(converter.convert(value));
    }

    @Override
    public PaymentDTO getById(Integer id) throws ServiceException {
        validator.validateIdNotNull(id);
        Payment result = dao.findById(id);
        if (Objects.isNull(result)){
            throw new EntityNotFoundException(THERE_IS_NO_SUCH_PAYMENT_EXCEPTION);
        }
        return converter.convert(result);
    }

    @Override
    public List<PaymentDTO> getAll() {
        List<PaymentDTO> result = new ArrayList<>();
        List<Payment> daoResult = dao.findAll();
        if (daoResult.isEmpty()){
            throw new EntityNotFoundException(REPOSITORY_IS_EMPTY_EXCEPTION);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }
}
