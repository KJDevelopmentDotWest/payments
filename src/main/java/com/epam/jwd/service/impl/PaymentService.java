package com.epam.jwd.service.impl;

import com.epam.jwd.dao.api.DAO;
import com.epam.jwd.dao.impl.PaymentDAO;
import com.epam.jwd.dao.model.payment.Payment;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.comparator.paymentcomparator.CommittedSortingComparator;
import com.epam.jwd.service.comparator.paymentcomparator.NameSortingComparator;
import com.epam.jwd.service.comparator.paymentcomparator.TimeSortingComparator;
import com.epam.jwd.service.comparator.paymentcomparator.UserIdSortingComparator;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.converter.impl.PaymentConverter;
import com.epam.jwd.service.dto.paymentdto.PaymentDTO;
import com.epam.jwd.service.exception.ExceptionCode;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;
import com.epam.jwd.service.validator.impl.PaymentValidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class PaymentService implements Service<PaymentDTO, Integer> {

    private final DAO<Payment, Integer> dao = new PaymentDAO();
    private final Validator<PaymentDTO, Integer> validator = new PaymentValidator();
    private final Converter<Payment, PaymentDTO, Integer> converter = new PaymentConverter();

    @Override
    public PaymentDTO create(PaymentDTO value) throws ServiceException {
        validator.validate(value, false);
        return converter.convert(dao.save(converter.convert(value)));
    }

    @Override
    public Boolean update(PaymentDTO value) throws ServiceException {
        validator.validate(value, true);
        return dao.update(converter.convert(value));
    }

    @Override
    public Boolean delete(PaymentDTO value) throws ServiceException {
        validator.validate(value, true);
        return dao.delete(converter.convert(value));
    }

    @Override
    public PaymentDTO getById(Integer id) throws ServiceException {
        validator.validateIdNotNull(id);
        Payment result = dao.findById(id);
        if (Objects.isNull(result)){
            throw new ServiceException(ExceptionCode.PAYMENT_IS_NOT_FOUND_EXCEPTION_CODE);
        }
        return converter.convert(result);
    }

    @Override
    public List<PaymentDTO> getAll() throws ServiceException {
        List<PaymentDTO> result = new ArrayList<>();
        List<Payment> daoResult = dao.findAll();
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }

    public List<PaymentDTO> getByUserId(Integer id) throws ServiceException {
        ((PaymentValidator)validator).validateUserId(id);
        List<PaymentDTO> result = new ArrayList<>();
        List<Payment> daoResult = ((PaymentDAO)dao).findByUserId(id);
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }

    public List<PaymentDTO> sortByUserId(List<PaymentDTO> payments) {
        payments.sort(new UserIdSortingComparator());
        return payments;
    }

    public List<PaymentDTO> sortByName(List<PaymentDTO> payments){
        payments.sort(new NameSortingComparator());
        return payments;
    }

    public List<PaymentDTO> sortByTime(List<PaymentDTO> payments){
        payments.sort(new TimeSortingComparator());
        return payments;
    }

    public List<PaymentDTO> sortByCommitted(List<PaymentDTO> payments){
        payments.sort(new CommittedSortingComparator());
        return payments;
    }
}
