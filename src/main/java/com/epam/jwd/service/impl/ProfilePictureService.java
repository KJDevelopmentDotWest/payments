package com.epam.jwd.service.impl;

import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.impl.ProfilePictureDao;
import com.epam.jwd.dao.model.profilepicture.ProfilePicture;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.converter.impl.ProfilePictureConverter;
import com.epam.jwd.service.dto.profilepicturedto.ProfilePictureDto;
import com.epam.jwd.service.exception.ExceptionCode;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Blocking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class ProfilePictureService implements Service<ProfilePictureDto, Integer> {

    private static final Logger logger = LogManager.getLogger(ProfilePictureService.class);

    private final Dao<ProfilePicture, Integer> dao = new ProfilePictureDao();
    private final Converter<ProfilePicture, ProfilePictureDto, Integer> converter = new ProfilePictureConverter();

    /**
     *
     * @throws ServiceException always, this operation is not supported
     */
    @Override
    public ProfilePictureDto create(ProfilePictureDto value) throws ServiceException {
        throw new ServiceException(ExceptionCode.OPERATION_IS_NOT_SUPPORTED_EXCEPTION_CODE);
    }

    /**
     *
     * @throws ServiceException always, this operation is not supported
     */
    @Override
    public Boolean update(ProfilePictureDto value) throws ServiceException {
        throw new ServiceException(ExceptionCode.OPERATION_IS_NOT_SUPPORTED_EXCEPTION_CODE);
    }

    /**
     *
     * @throws ServiceException always, this operation is not supported
     */
    @Override
    public Boolean delete(ProfilePictureDto value) throws ServiceException {
        throw new ServiceException(ExceptionCode.OPERATION_IS_NOT_SUPPORTED_EXCEPTION_CODE);
    }

    /**
     * @param id profile picture id
     * @return user with id == (profile pictures).id
     * @throws ServiceException if there is no profile picture with provided id or id is null
     */
    @Override
    public ProfilePictureDto getById(Integer id) throws ServiceException {
        logger.info("get by id method " + ProfilePictureService.class);
        Validator<ProfilePictureDto, Integer> validator = (value, checkId) -> {};
        validator.validateIdNotNull(id);
        ProfilePicture result = dao.findById(id);
        if (Objects.isNull(result)){
            throw new ServiceException(ExceptionCode.PROFILE_PICTURE_IS_NOT_FOUND_EXCEPTION_CODE);
        }
        return converter.convert(result);
    }

    /**
     *
     * @return list of profile pictures
     * @throws ServiceException if database is empty
     */
    @Override
    public List<ProfilePictureDto> getAll() throws ServiceException {
        logger.info("get all method " + ProfilePictureService.class);
        List<ProfilePictureDto> result = new ArrayList<>();
        List<ProfilePicture> daoResult = dao.findAll();
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(profilePicture -> result.add(converter.convert(profilePicture)));
        return result;
    }

    /**
     * @return number of profile pictures in database
     */
    @Override
    public Integer getAmount() {
        logger.info("get amount method " + ProfilePictureService.class);
        return dao.getRowsNumber();
    }
}
