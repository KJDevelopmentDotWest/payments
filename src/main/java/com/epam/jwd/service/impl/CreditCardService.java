package com.epam.jwd.service.impl;

import com.epam.jwd.dao.api.DAO;
import com.epam.jwd.dao.impl.CreditCardDAO;
import com.epam.jwd.dao.model.creditcard.CreditCard;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.converter.impl.CreditCardConverter;
import com.epam.jwd.service.dto.creditcarddto.CreditCardDTO;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;
import com.epam.jwd.service.validator.impl.CreditCardValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreditCardService implements Service<CreditCardDTO, Integer> {

    private final DAO<CreditCard, Integer> dao = new CreditCardDAO();
    private final Validator<CreditCardDTO, Integer> validator = new CreditCardValidator();
    private final Converter<CreditCard, CreditCardDTO, Integer> converter = new CreditCardConverter();

    private static final String THERE_IS_NO_SUCH_CREDIT_CARD_EXCEPTION = "there is no credit card with provided id";

    @Override
    public CreditCardDTO create(CreditCardDTO value) throws ServiceException {
        validator.validate(value);
        return converter.convert(dao.save(converter.convert(value)));
    }

    @Override
    public Boolean update(CreditCardDTO value) throws ServiceException {
        validator.validate(value);
        validator.validateIdNotNull(value);
        validator.validateIdNotNull(value.getBankAccount().getId());
        return dao.update(converter.convert(value));
    }

    @Override
    public Boolean delete(CreditCardDTO value) throws ServiceException {
        validator.validate(value);
        validator.validateIdNotNull(value);
        validator.validateIdNotNull(value.getBankAccount().getId());
        return dao.delete(converter.convert(value));
    }

    @Override
    public CreditCardDTO getById(Integer id) throws ServiceException {
        validator.validateIdNotNull(id);
        CreditCard result = dao.findById(id);
        if (Objects.isNull(result)){
            throw new ServiceException(THERE_IS_NO_SUCH_CREDIT_CARD_EXCEPTION);
        }
        return converter.convert(result);
    }

    @Override
    public List<CreditCardDTO> getAll() throws ServiceException {
        List<CreditCardDTO> result = new ArrayList<>();
        List<CreditCard> daoResult = dao.findAll();
        if (daoResult.isEmpty()){
            throw new ServiceException(REPOSITORY_IS_EMPTY_EXCEPTION);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }

    public List<CreditCardDTO> getByUserId(Integer id) throws ServiceException {
        validator.validateIdNotNull(id);
        List<CreditCardDTO> result = new ArrayList<>();
        List<CreditCard> daoResult = ((CreditCardDAO) dao).findByUserId(id);
        if (daoResult.isEmpty()){
            throw new ServiceException(REPOSITORY_IS_EMPTY_EXCEPTION);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }
}
