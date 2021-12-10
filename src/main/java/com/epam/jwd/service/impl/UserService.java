package com.epam.jwd.service.impl;

import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.impl.UserDao;
import com.epam.jwd.dao.model.user.User;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.converter.impl.UserConverter;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.exception.ExceptionCode;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;
import com.epam.jwd.service.validator.impl.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class UserService implements Service<UserDto, Integer> {

    private static final Logger logger = LogManager.getLogger(UserService.class);

    private final Dao<User, Integer> dao = new UserDao();
    private final Validator<UserDto, Integer> validator = new UserValidator();
    private final Converter<User, UserDto, Integer> converter = new UserConverter();

    /**
     * @param value user to be saved
     * @return saved user
     * @throws ServiceException if user is invalid or user cannot be created
     */
    @Override
    public UserDto create(UserDto value) throws ServiceException {
        logger.info("create method " + UserService.class);

        validator.validate(value, false);

        User checkUser = ((UserDao)dao).findByLogin(value.getLogin());
        if (!Objects.isNull(checkUser)){
            throw new ServiceException(ExceptionCode.USER_LOGIN_IS_NOT_UNIQUE_EXCEPTION_CODE);
        }

        User savedUser = dao.save(converter.convert(value));

        if (Objects.isNull(savedUser)){
            throw new ServiceException(ExceptionCode.USER_WAS_NOT_CREATED_EXCEPTION_CODE);
        }

        return converter.convert(savedUser);
    }

    /**
     * @param value user to be updated
     * @return true if user updated successfully, false otherwise
     * @throws ServiceException if user is invalid
     */
    @Override
    public Boolean update(UserDto value) throws ServiceException {
        logger.info("update method " + UserService.class);
        validator.validate(value, true);
        return dao.update(converter.convert(value));
    }

    /**
     * @param value user to be deleted
     * @return true if user deleted successfully, false otherwise
     * @throws ServiceException if user is invalid
     */
    @Override
    public Boolean delete(UserDto value) throws ServiceException {
        logger.info("delete method " + UserService.class);
        validator.validate(value, true);
        return dao.delete(converter.convert(value));
    }

    /**
     * @param id user id
     * @return user with id == user.id
     * @throws ServiceException if there is no user with provided id or id is null
     */
    @Override
    public UserDto getById(Integer id) throws ServiceException {
        logger.info("get by id method " + UserService.class);
        validator.validateIdNotNull(id);
        User result = dao.findById(id);
        if (Objects.isNull(result)){
            throw new ServiceException(ExceptionCode.USER_IS_NOT_FOUND_EXCEPTION_CODE);
        }
        return converter.convert(result);
    }

    /**
     *
     * @return list of users
     * @throws ServiceException if database is empty
     */
    @Override
    public List<UserDto> getAll() throws ServiceException {
        logger.info("get all method " + UserService.class);
        List<UserDto> result = new ArrayList<>();
        List<User> daoResult = dao.findAll();
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }

    /**
     * @return number of users in database
     */
    @Override
    public Integer getAmount() {
        logger.info("get amount method " + UserService.class);
        return dao.getRowsNumber();
    }

    /**
     *
     * @param login login of user
     * @return user with provided login
     * @throws ServiceException if there is no user with provided login
     */
    public UserDto getByLogin(String login) throws ServiceException {
        logger.info("get by login method " + UserService.class);
        ((UserValidator)validator).validateLoginNotNull(login);
        User result = ((UserDao)dao).findByLogin(login);
        if (Objects.isNull(result)){
            throw new ServiceException(ExceptionCode.USER_IS_NOT_FOUND_EXCEPTION_CODE);
        }
        return converter.convert(result);
    }

    /**
     *
     * @param limit amount of credit cards
     * @param offset offset from start of list in database
     * @return list of all users ordered by id
     * @throws ServiceException if repository is empty
     */
    public List<UserDto> getSortedByIdWithinRange(Integer limit, Integer offset) throws ServiceException {
        logger.info("get sorted by id within range method " + UserService.class);
        List<UserDto> result = new ArrayList<>();
        List<User> daoResult = ((UserDao)dao).findAllOrderedByIdWithinRange(limit, offset);
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }

    /**
     *
     * @param limit amount of users
     * @param offset offset from start of list in database
     * @return list of all users ordered by login
     * @throws ServiceException if repository is empty
     */
    public List<UserDto> getSortedByLoginWithinRange(Integer limit, Integer offset) throws ServiceException {
        logger.info("get sorted by login within range method " + UserService.class);
        List<UserDto> result = new ArrayList<>();
        List<User> daoResult = ((UserDao)dao).findAllOrderedByLoginWithinRange(limit, offset);
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }

    /**
     *
     * @param limit amount of users
     * @param offset offset from start of list in database
     * @return list of all users ordered by role
     * @throws ServiceException if repository is empty
     */
    public List<UserDto> getSortedByRoleWithinRange(Integer limit, Integer offset) throws ServiceException {
        logger.info("get sorted by role within range method " + UserService.class);
        List<UserDto> result = new ArrayList<>();
        List<User> daoResult = ((UserDao)dao).findAllOrderedByRoleWithinRange(limit, offset);
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }

    /**
     *
     * @param limit amount of users
     * @param offset offset from start of list in database
     * @return list of all users ordered by active
     * @throws ServiceException if repository is empty
     */
    public List<UserDto> getSortedByActiveWithinRange(Integer limit, Integer offset) throws ServiceException {
        logger.info("get sorted by active within range method " + UserService.class);
        List<UserDto> result = new ArrayList<>();
        List<User> daoResult = ((UserDao)dao).findAllOrderedByActiveWithinRange(limit, offset);
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }

    /**
     *
     * @param limit amount of users
     * @param offset offset from start of list in database
     * @return list of all users ordered by account name
     * @throws ServiceException if repository is empty
     */
    public List<UserDto> getSortedByAccountNameWithinRange(Integer limit, Integer offset) throws ServiceException {
        logger.info("get sorted by account name within range method " + UserService.class);
        List<UserDto> result = new ArrayList<>();
        List<User> daoResult = ((UserDao)dao).findAllOrderedByAccountNameWithinRange(limit, offset);
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }

    /**
     *
     * @param limit amount of users
     * @param offset offset from start of list in database
     * @return list of all users ordered by account surname
     * @throws ServiceException if repository is empty
     */
    public List<UserDto> getSortedByAccountSurnameWithinRange(Integer limit, Integer offset) throws ServiceException {
        logger.info("get sorted by account surname within range method " + UserService.class);
        List<UserDto> result = new ArrayList<>();
        List<User> daoResult = ((UserDao)dao).findAllOrderedByAccountSurnameWithinRange(limit, offset);
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }

    /**
     *
     * @param limit amount of users
     * @param offset offset from start of list in database
     * @return list of all users ordered by profile picture id
     * @throws ServiceException if repository is empty
     */
    public List<UserDto> getSortedByAccountProfilePictureIdWithinRange(Integer limit, Integer offset) throws ServiceException {
        logger.info("get sorted by account profile picture id within range method " + UserService.class);
        List<UserDto> result = new ArrayList<>();
        List<User> daoResult = ((UserDao)dao).findAllOrderedByAccountProfilePictureIdWithinRange(limit, offset);
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }
}
