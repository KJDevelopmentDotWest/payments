package com.epam.jwd.service.impl;

import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.impl.CreditCardDao;
import com.epam.jwd.dao.model.creditcard.CreditCard;
import com.epam.jwd.service.api.Service;
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

    /**
     * @param value credit card to be saved
     * @return saved credit card
     * @throws ServiceException if credit card is invalid or credit card cannot be created
     */
    @Override
    public CreditCardDto create(CreditCardDto value) throws ServiceException {
        logger.info("create method " + CreditCardService.class);
        validator.validate(value, false);

        CreditCard checkCreditCard = ((CreditCardDao)dao).findByCreditCardNumber(value.getCardNumber());
        if (!Objects.isNull(checkCreditCard)){
            throw new ServiceException(ExceptionCode.CREDIT_CARD_NUMBER_IS_NOT_UNIQUE_EXCEPTION_CODE);
        }

        CreditCard savedCreditCard = dao.save(converter.convert(value));

        if (Objects.isNull(savedCreditCard)){
            throw new ServiceException(ExceptionCode.CREDIT_CARD_WAS_NOT_CREATED);
        }

        return converter.convert(savedCreditCard);
    }

    /**
     * @param value credit card to be updated
     * @return true if credit card updated successfully, false otherwise
     * @throws ServiceException if credit card is invalid
     */
    @Override
    public Boolean update(CreditCardDto value) throws ServiceException {
        logger.info("update method " + CreditCardService.class);
        validator.validate(value, true);
        return dao.update(converter.convert(value));
    }

    /**
     * @param value credit card to be deleted
     * @return true if credit card deleted successfully, false otherwise
     * @throws ServiceException if credit card is invalid
     */
    @Override
    public Boolean delete(CreditCardDto value) throws ServiceException {
        logger.info("credit card deleted " + CreditCardService.class);
        validator.validate(value, true);
        validator.validateIdNotNull(value.getBankAccount().getId());
        return dao.delete(converter.convert(value));
    }

    /**
     * @param id credit card id
     * @return credit card with id == (credit card).id
     * @throws ServiceException if there is no credit card with provided id or id is null
     */
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

    /**
     *
     * @return list of credit cards
     * @throws ServiceException if database is empty
     */
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

    /**
     * @return number of credit cards in database
     */
    @Override
    public Integer getAmount() {
        logger.info("get amount method " + CreditCardService.class);
        return dao.getRowsNumber();
    }

    /**
     *
     * @param limit amount of credit cards
     * @param offset offset from start of list in database
     * @return list of all credit cards ordered by id
     * @throws ServiceException if repository is empty
     */
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

    /**
     *
     * @param limit amount of credit cards
     * @param offset offset from start of list in database
     * @return list of all credit cards ordered by user id
     * @throws ServiceException if repository is empty
     */
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

    /**
     *
     * @param limit amount of credit cards
     * @param offset offset from start of list in database
     * @return list of all credit cards ordered by name
     * @throws ServiceException if repository is empty
     */
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

    /**
     *
     * @param limit amount of credit cards
     * @param offset offset from start of list in database
     * @return list of all credit cards ordered by card number
     * @throws ServiceException if repository is empty
     */
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

    /**
     *
     * @param limit amount of credit cards
     * @param offset offset from start of list in database
     * @return list of all credit cards ordered by expire date
     * @throws ServiceException if repository is empty
     */
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

    /**
     *
     * @param limit amount of credit cards
     * @param offset offset from start of list in database
     * @return list of all credit cards ordered by bank account balance
     * @throws ServiceException if repository is empty
     */
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

    /**
     *
     * @param limit amount of credit cards
     * @param offset offset from start of list in database
     * @return list of all credit cards ordered by bank account state
     * @throws ServiceException if repository is empty
     */
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

    /**
     *
     * @param id id of user
     * @return amount of credit cards with id == userid
     * @throws ServiceException if id is null
     */
    public Integer getAmountWithUserId(Integer id) throws ServiceException {
        logger.info("get amount with user id method " + CreditCardService.class);
        validator.validateIdNotNull(id);
        return ((CreditCardDao)dao).getRowsNumberWithUserId(id);
    }

    /**
     *
     * @param id id of user
     * @return list of credit cards where id == userid
     */
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

    /**
     *
     * @param id id of user
     * @param limit amount of credit cards
     * @param offset offset from start of list in database
     * @return list of credit cards where id == userid
     * @throws ServiceException if there are no credit cards with id = userid
     */
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

    /**
     *
     * @param id id of user
     * @param limit amount of credit cards
     * @param offset offset from start of list in database
     * @return list of credit cards where id == userid ordered by name
     * @throws ServiceException if there are no credit cards with id = userid
     */
    public List<CreditCardDto> getByUserIdSortedByNameWithinRange(Integer id, Integer limit, Integer offset) throws ServiceException {
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

    /**
     *
     * @param id id of user
     * @param limit amount of credit cards
     * @param offset offset from start of list in database
     * @return list of credit cards where id == userid ordered by expire date
     * @throws ServiceException if there are no credit cards with id = userid
     */
    public List<CreditCardDto> getByUserIdSortedByExpireDateWithinRange(Integer id, Integer limit, Integer offset) throws ServiceException {
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

    /**
     *
     * @param id id of user
     * @param limit amount of credit cards
     * @param offset offset from start of list in database
     * @return list of credit cards where id == userid ordered by bank account balance
     * @throws ServiceException if there are no credit cards with id = userid
     */
    public List<CreditCardDto> getByUserIdSortedByBalanceWithinRange(Integer id, Integer limit, Integer offset) throws ServiceException {
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

    /**
     *
     * @param id id of user
     * @param limit amount of credit cards
     * @param offset offset from start of list in database
     * @return list of credit cards where id == userid ordered by bank account state
     * @throws ServiceException if there are no credit cards with id = userid
     */
    public List<CreditCardDto> getByUserIdSortedByStateWithinRange(Integer id, Integer limit, Integer offset) throws ServiceException {
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

    /**
     *
     * @param id id of user
     * @param limit amount of credit cards
     * @param offset offset from start of list in database
     * @return list of credit cards where id == userid ordered by number
     * @throws ServiceException if there are no credit cards with id = userid
     */
    public List<CreditCardDto> getByUserIdSortedByNumberWithinRange(Integer id, Integer limit, Integer offset) throws ServiceException {
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
}
