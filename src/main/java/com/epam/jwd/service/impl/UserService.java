package com.epam.jwd.service.impl;

import com.epam.jwd.dao.api.DAO;
import com.epam.jwd.dao.impl.UserDAO;
import com.epam.jwd.dao.model.user.User;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.converter.impl.UserConverter;
import com.epam.jwd.service.dto.userdto.UserDTO;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;
import com.epam.jwd.service.validator.impl.UserValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserService implements Service<UserDTO, Integer> {

    private final DAO<User, Integer> dao = new UserDAO();
    private final Validator<UserDTO, Integer> validator = new UserValidator();
    private final Converter<User, UserDTO, Integer> converter = new UserConverter();

    private static final String THERE_IS_NO_SUCH_USER_EXCEPTION = "there is no user with provided id";

    @Override
    public UserDTO create(UserDTO value) throws ServiceException {
        validator.validate(value);
        return converter.convert(dao.save(converter.convert(value)));
    }

    @Override
    public Boolean update(UserDTO value) throws ServiceException {
        validator.validate(value);
        validator.validateIdNotNull(value);
        validator.validateIdNotNull(value.getAccount().getId());
        return dao.update(converter.convert(value));
    }

    @Override
    public Boolean delete(UserDTO value) throws ServiceException {
        validator.validate(value);
        validator.validateIdNotNull(value);
        validator.validateIdNotNull(value.getAccount().getId());
        return dao.delete(converter.convert(value));
    }

    @Override
    public UserDTO getById(Integer id) throws ServiceException {
        validator.validateIdNotNull(id);
        User result = dao.findById(id);
        if (Objects.isNull(result)){
            throw new ServiceException(THERE_IS_NO_SUCH_USER_EXCEPTION);
        }
        return converter.convert(result);
    }

    @Override
    public List<UserDTO> getAll() throws ServiceException {
        List<UserDTO> result = new ArrayList<>();
        List<User> daoResult = dao.findAll();
        if (daoResult.isEmpty()){
            throw new ServiceException(REPOSITORY_IS_EMPTY_EXCEPTION);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }
}
