package com.epam.jwd.service.impl;

import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.impl.CreditCardDao;
import com.epam.jwd.dao.impl.PaymentDao;
import com.epam.jwd.dao.model.creditcard.CreditCard;
import com.epam.jwd.dao.model.payment.Payment;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.comparator.creditcardcomparator.BalanceSortingComparator;
import com.epam.jwd.service.comparator.creditcardcomparator.BlockedSortingComparator;
import com.epam.jwd.service.comparator.creditcardcomparator.UserIdSortingComparator;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.converter.impl.CreditCardConverter;
import com.epam.jwd.service.dto.creditcarddto.CreditCardDto;
import com.epam.jwd.service.dto.paymentdto.PaymentDto;
import com.epam.jwd.service.exception.ExceptionCode;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;
import com.epam.jwd.service.validator.impl.CreditCardValidator;
import com.epam.jwd.service.validator.impl.PaymentValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreditCardService implements Service<CreditCardDto, Integer> {

    private static final Logger logger = LogManager.getLogger(CreditCardService.class);

    private final Dao<CreditCard, Integer> dao = new CreditCardDao();
    private final Validator<CreditCardDto, Integer> validator = new CreditCardValidator();
    private final Converter<CreditCard, CreditCardDto, Integer> converter = new CreditCardConverter();

    @Override
    public CreditCardDto create(CreditCardDto value) throws ServiceException {
        logger.info("create method " + CreditCardService.class);
        validator.validate(value, false);

        CreditCard checkCreditCard = ((CreditCardDao)dao).findByCreditCardNumber(value.getCardNumber());
        ((CreditCardValidator)validator).validateCardNumberUnique(checkCreditCard);

        CreditCard createdCreditCard = converter.convert(value);

        return converter.convert(dao.save(createdCreditCard));
    }

    @Override
    public Boolean update(CreditCardDto value) throws ServiceException {
        logger.info("update method " + CreditCardService.class);
        validator.validate(value, true);
        return dao.update(converter.convert(value));
    }

    @Override
    public Boolean delete(CreditCardDto value) throws ServiceException {
        logger.info("credit card deleted " + CreditCardService.class);
        validator.validate(value, true);
        validator.validateIdNotNull(value.getBankAccount().getId());
        return dao.delete(converter.convert(value));
    }

    @Override
    public CreditCardDto getById(Integer id) throws ServiceException {
        logger.info("get by id method " + CreditCardService.class);
        validator.validateIdNotNull(id);
        CreditCard result = dao.findById(id);
        if (Objects.isNull(result)){
            throw new ServiceException(ExceptionCode.CREDIT_CARD_IS_NOT_FOUND_EXCEPTION_CODE);
        }
        return converter.convert(result);
    }

    @Override
    public List<CreditCardDto> getAll() throws ServiceException {
        logger.info("get all method " + CreditCardService.class);
        List<CreditCardDto> result = new ArrayList<>();
        List<CreditCard> daoResult = dao.findAll();
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }

    @Override
    public Integer getAmount() {
        logger.info("get amount method " + CreditCardService.class);
        return dao.getRowsNumber();
    }

    public List<CreditCardDto> getSortedByIdWithinRange(Integer limit, Integer offset) throws ServiceException {
        logger.info("get sorted by id within range method " + CreditCardService.class);
        List<CreditCardDto> result = new ArrayList<>();
        List<CreditCard> daoResult = ((CreditCardDao)dao).findAllOrderedByIdWithinRange(limit, offset);
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }

    public List<CreditCardDto> getSortedByUserIdWithinRange(Integer limit, Integer offset) throws ServiceException {
        logger.info("get sorted by id within range method " + CreditCardService.class);
        List<CreditCardDto> result = new ArrayList<>();
        List<CreditCard> daoResult = ((CreditCardDao)dao).findAllOrderedByUserIdWithinRange(limit, offset);
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }

    public List<CreditCardDto> getSortedByNameWithinRange(Integer limit, Integer offset) throws ServiceException {
        logger.info("get sorted by id within range method " + CreditCardService.class);
        List<CreditCardDto> result = new ArrayList<>();
        List<CreditCard> daoResult = ((CreditCardDao)dao).findAllOrderedByNameWithinRange(limit, offset);
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }

    public List<CreditCardDto> getSortedByNumberWithinRange(Integer limit, Integer offset) throws ServiceException {
        logger.info("get sorted by id within range method " + CreditCardService.class);
        List<CreditCardDto> result = new ArrayList<>();
        List<CreditCard> daoResult = ((CreditCardDao)dao).findAllOrderedByNumberWithinRange(limit, offset);
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }

    public List<CreditCardDto> getSortedByExpireDateWithinRange(Integer limit, Integer offset) throws ServiceException {
        logger.info("get sorted by id within range method " + CreditCardService.class);
        List<CreditCardDto> result = new ArrayList<>();
        List<CreditCard> daoResult = ((CreditCardDao)dao).findAllOrderedByExpireDateWithinRange(limit, offset);
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }

    public List<CreditCardDto> getSortedByBalanceWithinRange(Integer limit, Integer offset) throws ServiceException {
        logger.info("get sorted by id within range method " + CreditCardService.class);
        List<CreditCardDto> result = new ArrayList<>();
        List<CreditCard> daoResult = ((CreditCardDao)dao).findAllOrderedByBalanceWithinRange(limit, offset);
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }

    public List<CreditCardDto> getSortedByStateWithinRange(Integer limit, Integer offset) throws ServiceException {
        logger.info("get sorted by id within range method " + CreditCardService.class);
        List<CreditCardDto> result = new ArrayList<>();
        List<CreditCard> daoResult = ((CreditCardDao)dao).findAllOrderedByStateWithinRange(limit, offset);
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }

    public Integer getAmountWithUserId(Integer id) throws ServiceException {
        logger.info("get amount with user id method " + PaymentService.class);
        validator.validateIdNotNull(id);
        return ((CreditCardDao)dao).getRowsNumberWithUserId(id);
    }

    public List<CreditCardDto> getByUserId(Integer id) throws ServiceException {
        logger.info("get by user id method " + CreditCardService.class);
        validator.validateIdNotNull(id);
        List<CreditCardDto> result = new ArrayList<>();
        List<CreditCard> daoResult = ((CreditCardDao) dao).findByUserId(id);
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.CREDIT_CARD_IS_NOT_FOUND_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }

    public List<CreditCardDto> getByUserIdWithinRange(Integer id, Integer limit, Integer offset) throws ServiceException {
        logger.info("get by user id within range method " + CreditCardService.class);
        ((CreditCardValidator)validator).validateUserId(id);
        List<CreditCardDto> result = new ArrayList<>();
        List<CreditCard> daoResult = ((CreditCardDao)dao).findByUserIdWithinRange(id, limit, offset);
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }

    public List<CreditCardDto> getByUserIdOrderedByNameWithinRange(Integer id, Integer limit, Integer offset) throws ServiceException {
        logger.info("get by user id ordered by name within range method " + CreditCardService.class);
        ((CreditCardValidator)validator).validateUserId(id);
        List<CreditCardDto> result = new ArrayList<>();
        List<CreditCard> daoResult = ((CreditCardDao)dao).findByUserIdOrderedByNameWithinRange(id, limit, offset);
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }

    public List<CreditCardDto> getByUserIdOrderedByExpireDateWithinRange(Integer id, Integer limit, Integer offset) throws ServiceException {
        logger.info("get by user id ordered by expire date within range method " + CreditCardService.class);
        ((CreditCardValidator)validator).validateUserId(id);
        List<CreditCardDto> result = new ArrayList<>();
        List<CreditCard> daoResult = ((CreditCardDao)dao).findByUserIdOrderedByExpireDateWithinRange(id, limit, offset);
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }

    public List<CreditCardDto> getByUserIdOrderedByBalanceWithinRange(Integer id, Integer limit, Integer offset) throws ServiceException {
        logger.info("get by user id ordered by balance within range method " + CreditCardService.class);
        ((CreditCardValidator)validator).validateUserId(id);
        List<CreditCardDto> result = new ArrayList<>();
        List<CreditCard> daoResult = ((CreditCardDao)dao).findByUserIdOrderedByBalanceWithinRange(id, limit, offset);
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }

    public List<CreditCardDto> getByUserIdOrderedByStateWithinRange(Integer id, Integer limit, Integer offset) throws ServiceException {
        logger.info("get by user id ordered by state within range method " + CreditCardService.class);
        ((CreditCardValidator)validator).validateUserId(id);
        List<CreditCardDto> result = new ArrayList<>();
        List<CreditCard> daoResult = ((CreditCardDao)dao).findByUserIdOrderedByStateWithinRange(id, limit, offset);
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }

    public List<CreditCardDto> getByUserIdOrderedByNumberWithinRange(Integer id, Integer limit, Integer offset) throws ServiceException {
        logger.info("get by user id ordered by number within range method " + CreditCardService.class);
        ((CreditCardValidator)validator).validateUserId(id);
        List<CreditCardDto> result = new ArrayList<>();
        List<CreditCard> daoResult = ((CreditCardDao)dao).findByUserIdOrderedByNumberWithinRange(id, limit, offset);
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }

    public List<CreditCardDto> sortByUserId(List<CreditCardDto> creditCards) {
        logger.info("sorted by user id " + CreditCardService.class);
        creditCards.sort(new UserIdSortingComparator());
        return creditCards;
    }

    public List<CreditCardDto> sortByBalance(List<CreditCardDto> creditCards) {
        logger.info("sorted by balance " + CreditCardService.class);
        creditCards.sort(new BalanceSortingComparator());
        return creditCards;
    }

    public List<CreditCardDto> sortByBlocked(List<CreditCardDto> creditCards) {
        logger.info("sorted by is blocked " + CreditCardService.class);
        creditCards.sort(new BlockedSortingComparator());
        return creditCards;
    }
}
