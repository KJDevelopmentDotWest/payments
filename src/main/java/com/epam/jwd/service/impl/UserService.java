package com.epam.jwd.service.impl;

import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.impl.UserDao;
import com.epam.jwd.dao.model.user.User;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.comparator.usercomparator.AccountSurnameSortingComparator;
import com.epam.jwd.service.comparator.usercomparator.RoleSortingComparator;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.converter.impl.UserConverter;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.exception.ExceptionCode;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;
import com.epam.jwd.service.validator.impl.UserValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserService implements Service<UserDto, Integer> {

    private final Dao<User, Integer> dao = new UserDao();
    private final Validator<UserDto, Integer> validator = new UserValidator();
    private final Converter<User, UserDto, Integer> converter = new UserConverter();

    @Override
    public UserDto create(UserDto value) throws ServiceException {
        validator.validate(value, false);

        User checkUser = ((UserDao)dao).findByLogin(value.getLogin());
        ((UserValidator)validator).validateLoginUnique(checkUser);

        User createdUser = converter.convert(value);
        return converter.convert(dao.save(createdUser));
    }

    @Override
    public Boolean update(UserDto value) throws ServiceException {
        validator.validate(value, true);
        return dao.update(converter.convert(value));
    }

    @Override
    public Boolean delete(UserDto value) throws ServiceException {
        validator.validate(value, true);
        return dao.delete(converter.convert(value));
    }

    @Override
    public UserDto getById(Integer id) throws ServiceException {
        validator.validateIdNotNull(id);
        User result = dao.findById(id);
        if (Objects.isNull(result)){
            throw new ServiceException(ExceptionCode.USER_IS_NOT_FOUND_EXCEPTION_CODE);
        }
        return converter.convert(result);
    }

    @Override
    public List<UserDto> getAll() throws ServiceException {
        List<UserDto> result = new ArrayList<>();
        List<User> daoResult = dao.findAll();
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }

    public UserDto getByLogin(String login) throws ServiceException {
        ((UserValidator)validator).validateLoginNotNull(login);
        User result = ((UserDao)dao).findByLogin(login);
        if (Objects.isNull(result)){
            throw new ServiceException(ExceptionCode.USER_IS_NOT_FOUND_EXCEPTION_CODE);
        }
        return converter.convert(result);
    }

    public List<UserDto> sortByAccountSurname (List<UserDto> users){
        users.sort(new AccountSurnameSortingComparator());
        return users;
    }

    public List<UserDto> sortByRole (List<UserDto> users){
        users.sort(new RoleSortingComparator());
        return users;
    }
}
