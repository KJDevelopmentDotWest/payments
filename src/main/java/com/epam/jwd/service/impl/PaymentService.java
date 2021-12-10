package com.epam.jwd.service.impl;

import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.impl.PaymentDao;
import com.epam.jwd.dao.model.payment.Payment;
import com.epam.jwd.service.api.Service;
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

    /**
     * @param value payment to be saved
     * @return payment card
     * @throws ServiceException if payment is invalid or payment cannot be created
     */
    @Override
    public PaymentDto create(PaymentDto value) throws ServiceException {
        logger.info("create method " + PaymentService.class);
        validator.validate(value, false);
        Payment savedPayment = dao.save(converter.convert(value));

        if (Objects.isNull(savedPayment)){
            throw new ServiceException(ExceptionCode.PAYMENT_WAS_NOT_CREATED);
        }

        return converter.convert(savedPayment);
    }

    /**
     * @param value payment to be updated
     * @return true if payment updated successfully, false otherwise
     * @throws ServiceException if payment is invalid
     */
    @Override
    public Boolean update(PaymentDto value) throws ServiceException {
        logger.info("update method " + PaymentService.class);
        validator.validate(value, true);
        return dao.update(converter.convert(value));
    }

    /**
     * @param value payment to be deleted
     * @return true if payment deleted successfully, false otherwise
     * @throws ServiceException if payment is invalid
     */
    @Override
    public Boolean delete(PaymentDto value) throws ServiceException {
        logger.info("delete method " + PaymentService.class);
        validator.validate(value, true);
        return dao.delete(converter.convert(value));
    }

    /**
     * @param id payment id
     * @return payment with id == payment.id
     * @throws ServiceException if there is no payment with provided id or id is null
     */
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

    /**
     *
     * @return list of payments
     * @throws ServiceException if database is empty
     */
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

    /**
     * @return number of payments in database
     */
    @Override
    public Integer getAmount() {
        logger.info("get amount method " + PaymentService.class);
        return dao.getRowsNumber();
    }

    /**
     *
     * @param id id of user
     * @return amount of payments with id == userid
     * @throws ServiceException if id is null
     */
    public Integer getAmountWithUserId(Integer id) throws ServiceException {
        logger.info("get amount with user id method " + PaymentService.class);
        validator.validateIdNotNull(id);
        return ((PaymentDao)dao).getRowsNumberWithUserId(id);
    }

    /**
     *
     * @param limit amount of payments
     * @param offset offset from start of list in database
     * @return list of all payments ordered by id
     * @throws ServiceException if repository is empty
     */
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

    /**
     *
     * @param limit amount of payments
     * @param offset offset from start of list in database
     * @return list of all payments ordered by user id
     * @throws ServiceException if repository is empty
     */
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

    /**
     *
     * @param limit amount of payments
     * @param offset offset from start of list in database
     * @return list of all payments ordered by name
     * @throws ServiceException if repository is empty
     */
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

    /**
     *
     * @param limit amount of payments
     * @param offset offset from start of list in database
     * @return list of all payments ordered by price
     * @throws ServiceException if repository is empty
     */
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

    /**
     *
     * @param limit amount of payments
     * @param offset offset from start of list in database
     * @return list of all payments ordered by destination address
     * @throws ServiceException if repository is empty
     */
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

    /**
     *
     * @param limit amount of payments
     * @param offset offset from start of list in database
     * @return list of all payments ordered by transaction time
     * @throws ServiceException if repository is empty
     */
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

    /**
     *
     * @param limit amount of payments
     * @param offset offset from start of list in database
     * @return list of all payments ordered by committed
     * @throws ServiceException if repository is empty
     */
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

    /**
     *
     * @param id id of user
     * @return list of payments where id == userid
     */
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

    /**
     *
     * @param id id of user
     * @param limit amount of payments
     * @param offset offset from start of list in database
     * @return list of payments where id == userid
     * @throws ServiceException if there are no payments with id = userid
     */
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

    /**
     *
     * @param id id of user
     * @param limit amount of payments
     * @param offset offset from start of list in database
     * @return list of payments where id == userid ordered by name
     * @throws ServiceException if there are no payments with id = userid
     */
    public List<PaymentDto> getByUserIdSortedByNameWithinRange(Integer id, Integer limit, Integer offset) throws ServiceException {
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

    /**
     *
     * @param id id of user
     * @param limit amount of payments
     * @param offset offset from start of list in database
     * @return list of payments where id == userid ordered by price
     * @throws ServiceException if there are no payments with id = userid
     */
    public List<PaymentDto> getByUserIdSortedByPriceWithinRange(Integer id, Integer limit, Integer offset) throws ServiceException {
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

    /**
     *
     * @param id id of user
     * @param limit amount of payments
     * @param offset offset from start of list in database
     * @return list of payments where id == userid ordered by destination address
     * @throws ServiceException if there are no payments with id = userid
     */
    public List<PaymentDto> getByUserIdSortedByDestinationAddressWithinRange(Integer id, Integer limit, Integer offset) throws ServiceException {
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

    /**
     *
     * @param id id of user
     * @param limit amount of payments
     * @param offset offset from start of list in database
     * @return list of payments where id == userid ordered by transaction time
     * @throws ServiceException if there are no payments with id = userid
     */
    public List<PaymentDto> getByUserIdSortedByTimeWithinRange(Integer id, Integer limit, Integer offset) throws ServiceException {
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

    /**
     *
     * @param id id of user
     * @param limit amount of payments
     * @param offset offset from start of list in database
     * @return list of payments where id == userid ordered by committed
     * @throws ServiceException if there are no payments with id = userid
     */
    public List<PaymentDto> getByUserIdSortedByCommittedWithinRange(Integer id, Integer limit, Integer offset) throws ServiceException {
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
}
