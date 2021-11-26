package com.epam.jwd.service.impl;

import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.impl.PaymentDao;
import com.epam.jwd.dao.model.payment.Payment;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.comparator.paymentcomparator.CommittedSortingComparator;
import com.epam.jwd.service.comparator.paymentcomparator.NameSortingComparator;
import com.epam.jwd.service.comparator.paymentcomparator.TimeSortingComparator;
import com.epam.jwd.service.comparator.paymentcomparator.UserIdSortingComparator;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.converter.impl.PaymentConverter;
import com.epam.jwd.service.dto.paymentdto.PaymentDto;
import com.epam.jwd.service.exception.ExceptionCode;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;
import com.epam.jwd.service.validator.impl.PaymentValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PaymentService implements Service<PaymentDto, Integer> {

    private static final Logger logger = LogManager.getLogger(PaymentService.class);

    private final Dao<Payment, Integer> dao = new PaymentDao();
    private final Validator<PaymentDto, Integer> validator = new PaymentValidator();
    private final Converter<Payment, PaymentDto, Integer> converter = new PaymentConverter();

    @Override
    public PaymentDto create(PaymentDto value) throws ServiceException {
        logger.info("create method " + PaymentService.class);
        validator.validate(value, false);
        return converter.convert(dao.save(converter.convert(value)));
    }

    @Override
    public Boolean update(PaymentDto value) throws ServiceException {
        logger.info("update method " + PaymentService.class);
        validator.validate(value, true);
        return dao.update(converter.convert(value));
    }

    @Override
    public Boolean delete(PaymentDto value) throws ServiceException {
        logger.info("delete method " + PaymentService.class);
        validator.validate(value, true);
        return dao.delete(converter.convert(value));
    }

    @Override
    public PaymentDto getById(Integer id) throws ServiceException {
        logger.info("get by id method " + PaymentService.class);
        validator.validateIdNotNull(id);
        Payment result = dao.findById(id);
        if (Objects.isNull(result)){
            throw new ServiceException(ExceptionCode.PAYMENT_IS_NOT_FOUND_EXCEPTION_CODE);
        }
        return converter.convert(result);
    }

    @Override
    public List<PaymentDto> getAll() throws ServiceException {
        logger.info("get all method " + PaymentService.class);
        List<PaymentDto> result = new ArrayList<>();
        List<Payment> daoResult = dao.findAll();
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }

    @Override
    public Integer getAmount() {
        logger.info("get amount method " + PaymentService.class);
        return dao.getRowsNumber();
    }

    public Integer getAmountWithUserId(Integer id) throws ServiceException {
        logger.info("get amount with user id method " + PaymentService.class);
        validator.validateIdNotNull(id);
        return ((PaymentDao)dao).getRowsNumberWithUserId(id);
    }

    public List<PaymentDto> getSortedByIdWithinRange(Integer limit, Integer offset) throws ServiceException {
        logger.info("get sorted by id within range method " + PaymentService.class);
        List<PaymentDto> result = new ArrayList<>();
        List<Payment> daoResult = ((PaymentDao)dao).findAllOrderedByIdWithinRange(limit, offset);
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }

    public List<PaymentDto> getSortedByUserIdWithinRange(Integer limit, Integer offset) throws ServiceException {
        logger.info("get sorted by user id within range method " + PaymentService.class);
        List<PaymentDto> result = new ArrayList<>();
        List<Payment> daoResult = ((PaymentDao)dao).findAllOrderedByUserIdWithinRange(limit, offset);
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }

    public List<PaymentDto> getSortedByNameWithinRange(Integer limit, Integer offset) throws ServiceException {
        logger.info("get sorted by name within range method " + PaymentService.class);
        List<PaymentDto> result = new ArrayList<>();
        List<Payment> daoResult = ((PaymentDao)dao).findAllOrderedByNameWithinRange(limit, offset);
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }

    public List<PaymentDto> getSortedByPriceWithinRange(Integer limit, Integer offset) throws ServiceException {
        logger.info("get sorted by price within range method " + PaymentService.class);
        List<PaymentDto> result = new ArrayList<>();
        List<Payment> daoResult = ((PaymentDao)dao).findAllOrderedByPriceWithinRange(limit, offset);
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }

    public List<PaymentDto> getSortedByDestinationAddressWithinRange(Integer limit, Integer offset) throws ServiceException {
        logger.info("get sorted by address within range method " + PaymentService.class);
        List<PaymentDto> result = new ArrayList<>();
        List<Payment> daoResult = ((PaymentDao)dao).findAllOrderedByAddressWithinRange(limit, offset);
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }

    public List<PaymentDto> getSortedByTimeWithinRange(Integer limit, Integer offset) throws ServiceException {
        logger.info("get sorted by time within range method " + PaymentService.class);
        List<PaymentDto> result = new ArrayList<>();
        List<Payment> daoResult = ((PaymentDao)dao).findAllOrderedByTimeWithinRange(limit, offset);
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }

    public List<PaymentDto> getSortedByCommittedWithinRange(Integer limit, Integer offset) throws ServiceException {
        logger.info("get sorted by committed within range method " + PaymentService.class);
        List<PaymentDto> result = new ArrayList<>();
        List<Payment> daoResult = ((PaymentDao)dao).findAllOrderedByCommittedWithinRange(limit, offset);
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }

    public List<PaymentDto> getByUserId(Integer id) throws ServiceException {
        logger.info("get by user id method " + PaymentService.class);
        ((PaymentValidator)validator).validateUserId(id);
        List<PaymentDto> result = new ArrayList<>();
        List<Payment> daoResult = ((PaymentDao)dao).findByUserId(id);
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }

    public List<PaymentDto> getByUserIdWithinRange(Integer id, Integer limit, Integer offset) throws ServiceException {
        logger.info("get by user id within range method " + PaymentService.class);
        ((PaymentValidator)validator).validateUserId(id);
        List<PaymentDto> result = new ArrayList<>();
        List<Payment> daoResult = ((PaymentDao)dao).findByUserIdWithinRange(id, limit, offset);
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }

    public List<PaymentDto> getByUserIdOrderedByNameWithinRange(Integer id, Integer limit, Integer offset) throws ServiceException {
        logger.info("get by user id ordered by name within range method " + PaymentService.class);
        ((PaymentValidator)validator).validateUserId(id);
        List<PaymentDto> result = new ArrayList<>();
        List<Payment> daoResult = ((PaymentDao)dao).findByUserIdOrderedByNameWithinRange(id, limit, offset);
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }

    public List<PaymentDto> getByUserIdOrderedByPriceWithinRange(Integer id, Integer limit, Integer offset) throws ServiceException {
        logger.info("get by user id ordered by price within range method " + PaymentService.class);
        ((PaymentValidator)validator).validateUserId(id);
        List<PaymentDto> result = new ArrayList<>();
        List<Payment> daoResult = ((PaymentDao)dao).findByUserIdOrderedByPriceWithinRange(id, limit, offset);
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }

    public List<PaymentDto> getByUserIdOrderedByDestinationAddressWithinRange(Integer id, Integer limit, Integer offset) throws ServiceException {
        logger.info("get by user id ordered by destination address within range method " + PaymentService.class);
        ((PaymentValidator)validator).validateUserId(id);
        List<PaymentDto> result = new ArrayList<>();
        List<Payment> daoResult = ((PaymentDao)dao).findByUserIdOrderedByDestinationWithinRange(id, limit, offset);
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }

    public List<PaymentDto> getByUserIdOrderedByTimeWithinRange(Integer id, Integer limit, Integer offset) throws ServiceException {
        logger.info("get by user id ordered by time within range method " + PaymentService.class);
        ((PaymentValidator)validator).validateUserId(id);
        List<PaymentDto> result = new ArrayList<>();
        List<Payment> daoResult = ((PaymentDao)dao).findByUserIdOrderedByTimeWithinRange(id, limit, offset);
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }

    public List<PaymentDto> getByUserIdOrderedByCommittedWithinRange(Integer id, Integer limit, Integer offset) throws ServiceException {
        logger.info("get by user id ordered by committed within range method " + PaymentService.class);
        ((PaymentValidator)validator).validateUserId(id);
        List<PaymentDto> result = new ArrayList<>();
        List<Payment> daoResult = ((PaymentDao)dao).findByUserIdOrderedByCommittedWithinRange(id, limit, offset);
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }

    public List<PaymentDto> sortByUserId(List<PaymentDto> payments) {
        logger.info("sorted by user id " + PaymentService.class);
        payments.sort(new UserIdSortingComparator());
        return payments;
    }

    public List<PaymentDto> sortByName(List<PaymentDto> payments){
        logger.info("sorted by name " + PaymentService.class);
        payments.sort(new NameSortingComparator());
        return payments;
    }

    public List<PaymentDto> sortByTime(List<PaymentDto> payments){
        logger.info("sorted by time " + PaymentService.class);
        payments.sort(new TimeSortingComparator());
        return payments;
    }

    public List<PaymentDto> sortByCommitted(List<PaymentDto> payments){
        logger.info("sorted by is committed " + PaymentService.class);
        payments.sort(new CommittedSortingComparator());
        return payments;
    }
}
