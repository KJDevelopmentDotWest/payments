package com.epam.jwd.service.impl;

import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.impl.CreditCardDao;
import com.epam.jwd.dao.model.creditcard.CreditCard;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.comparator.creditcardcomparator.BalanceSortingComparator;
import com.epam.jwd.service.comparator.creditcardcomparator.BlockedSortingComparator;
import com.epam.jwd.service.comparator.creditcardcomparator.UserIdSortingComparator;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.converter.impl.CreditCardConverter;
import com.epam.jwd.service.dto.creditcarddto.CreditCardDto;
import com.epam.jwd.service.exception.ExceptionCode;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;
import com.epam.jwd.service.validator.impl.CreditCardValidator;
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
        return converter.convert(dao.save(converter.convert(value)));
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
