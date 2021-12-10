package com.epam.jwd.service.impl;

import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.impl.AccountDao;
import com.epam.jwd.dao.model.user.Account;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.converter.impl.AccountConverter;
import com.epam.jwd.service.dto.userdto.AccountDto;
import com.epam.jwd.service.exception.ExceptionCode;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;
import com.epam.jwd.service.validator.impl.AccountValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AccountService implements Service<AccountDto, Integer> {

    private static final Logger logger = LogManager.getLogger(AccountService.class);

    private final Dao<Account, Integer> dao = new AccountDao();
    private final Validator<AccountDto, Integer> validator = new AccountValidator();
    private final Converter<Account, AccountDto, Integer> converter = new AccountConverter();

    /**
     *
     * @param value account to be saved
     * @return saved account
     * @throws ServiceException if account is invalid or account cannot be created
     */
    @Override
    public AccountDto create(AccountDto value) throws ServiceException {
        logger.info("create method " + AccountService.class);
        validator.validate(value, false);
        Account savedAccount = dao.save(converter.convert(value));
        if (Objects.isNull(savedAccount)){
            throw new ServiceException(ExceptionCode.ACCOUNT_WAS_NOT_CREATED_EXCEPTION_CODE);
        }
        return converter.convert(savedAccount);
    }

    /**
     * @param value account to be updated
     * @return true if account updated successfully, false otherwise
     * @throws ServiceException if account is invalid
     */
    @Override
    public Boolean update(AccountDto value) throws ServiceException {
        logger.info("update method " + AccountService.class);
        validator.validate(value, true);
        return dao.update(converter.convert(value));
    }

    /**
     * @param value account to be deleted
     * @return true if account deleted successfully, false otherwise
     * @throws ServiceException if account is invalid
     */
    @Override
    public Boolean delete(AccountDto value) throws ServiceException {
        logger.info("delete method " + AccountService.class);
        validator.validate(value, true);
        return dao.delete(converter.convert(value));
    }

    /**
     * @param id account id
     * @return account with id == account.id
     * @throws ServiceException if there is no account with provided id or id is null
     */
    @Override
    public AccountDto getById(Integer id) throws ServiceException {
        logger.info("get by id " + AccountService.class);
        validator.validateIdNotNull(id);
        Account result = dao.findById(id);
        if (Objects.isNull(result)){
            throw new ServiceException(ExceptionCode.ACCOUNT_IS_NOT_FOUND_EXCEPTION_CODE);
        }
        return converter.convert(result);
    }

    /**
     *
     * @return list of accounts
     * @throws ServiceException if database is empty
     */
    @Override
    public List<AccountDto> getAll() throws ServiceException {
        logger.info("get all method " + AccountService.class);
        List<AccountDto> result = new ArrayList<>();
        List<Account> daoResult = dao.findAll();
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }

    /**
     * @return number of accounts in database
     */
    @Override
    public Integer getAmount() {
        logger.info("get amount method " + AccountService.class);
        return dao.getRowsNumber();
    }
}
