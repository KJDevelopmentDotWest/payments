package com.epam.jwd.service.api;

import com.epam.jwd.service.dto.EntityDTO;
import com.epam.jwd.service.exception.ServiceException;

import java.util.List;

public interface Service<T extends EntityDTO<K>, K> {
    String REPOSITORY_IS_EMPTY_EXCEPTION = "repository is empty";

    T create(T value) throws ServiceException;
    Boolean update(T value) throws ServiceException;
    Boolean delete(T value) throws ServiceException;
    T getById(K id) throws ServiceException;
    List<T> getAll() throws ServiceException;
}
