package com.epam.jwd.service.impl;

import com.epam.jwd.dao.api.DAO;
import com.epam.jwd.dao.impl.RoleDAO;
import com.epam.jwd.dao.model.user.Role;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.converter.impl.RoleConverter;
import com.epam.jwd.service.dto.userdto.RoleDTO;
import com.epam.jwd.service.exception.ExceptionCode;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;
import com.epam.jwd.service.validator.impl.RoleValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RoleService implements Service<RoleDTO, Integer> {

    private final DAO<Role, Integer> dao = new RoleDAO();
    private final Validator<RoleDTO, Integer> validator = new RoleValidator();
    private final Converter<Role, RoleDTO, Integer> converter = new RoleConverter();

    @Override
    public RoleDTO create(RoleDTO value) throws ServiceException {
        validator.validate(value, false);
        return converter.convert(dao.save(converter.convert(value)));
    }

    @Override
    public Boolean update(RoleDTO value) throws ServiceException {
        validator.validate(value, true);
        return dao.update(converter.convert(value));
    }

    @Override
    public Boolean delete(RoleDTO value) throws ServiceException {
        validator.validate(value, true);
        return dao.delete(converter.convert(value));
    }

    @Override
    public RoleDTO getById(Integer id) throws ServiceException {
        validator.validateIdNotNull(id);
        Role result = dao.findById(id);
        if (Objects.isNull(result)){
            throw new ServiceException(ExceptionCode.ROLE_IS_NOT_FOUND_EXCEPTION_CODE);
        }
        return converter.convert(result);
    }

    @Override
    public List<RoleDTO> getAll() throws ServiceException {
        List<RoleDTO> result = new ArrayList<>();
        List<Role> daoResult = dao.findAll();
        if (daoResult.isEmpty()){
            throw new ServiceException(ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE);
        }
        daoResult.forEach(user -> result.add(converter.convert(user)));
        return result;
    }
}
