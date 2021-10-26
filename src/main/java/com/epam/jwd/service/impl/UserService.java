package com.epam.jwd.service.impl;

import com.epam.jwd.dao.api.DAO;
import com.epam.jwd.dao.impl.UserDAO;
import com.epam.jwd.dao.model.user.User;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.comparator.usercomparator.AccountSurnameSortingComparator;
import com.epam.jwd.service.comparator.usercomparator.RoleSortingComparator;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.converter.impl.UserConverter;
import com.epam.jwd.service.dto.userdto.UserDTO;
import com.epam.jwd.service.exception.ExceptionCode;
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

    @Override
    public UserDTO create(UserDTO value) throws ServiceException {
        validator.validate(value, false);

        User checkUser = ((UserDAO)dao).findByLogin(value.getLogin());
        ((UserValidator)validator).validateLoginUnique(checkUser);

        User createdUser = converter.convert(value);
        return converter.convert(dao.save(createdUser));
    }

    @Override
    public Boolean update(UserDTO value) throws ServiceException {
        validator.validate(value, true);
        return dao.update(converter.convert(value));
    }

    @Override
    public Boolean delete(UserDTO value) throws ServiceException {
        validator.validate(value, true);
        return dao.delete(converter.convert(value));
    }

    @Override
    public UserDTO getById(Integer id) throws ServiceException {
        validator.validateIdNotNull(id);
        User result = dao.findById(id);
        if (Objects.isNull(result)){
            throw new ServiceException(ExceptionCode.USER_IS_NOT_FOUND_EXCEPTION_CODE);
        }
        return converter.convert(result);
    }

    @Override
    public List<UserDTO> getAll() throws ServiceException {
        List<UserDTO> result = new ArrayList<>();
        List<User> daoResult = dao.findAll();
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }

    public UserDTO getByLogin(String login) throws ServiceException {
        ((UserValidator)validator).validateLoginNotNull(login);
        User result = ((UserDAO)dao).findByLogin(login);
        if (Objects.isNull(result)){
            throw new ServiceException(ExceptionCode.USER_IS_NOT_FOUND_EXCEPTION_CODE);
        }
        return converter.convert(result);
    }

    public List<UserDTO> sortByAccountSurname (List<UserDTO> users){
        users.sort(new AccountSurnameSortingComparator());
        return users;
    }

    public List<UserDTO> sortByRole (List<UserDTO> users){
        users.sort(new RoleSortingComparator());
        return users;
    }
}
