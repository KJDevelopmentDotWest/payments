package com.epam.jwd.service.api;

import com.epam.jwd.service.dto.AbstractDto;
import com.epam.jwd.service.exception.ServiceException;

import java.util.List;

public interface Service<T extends AbstractDto<K>, K> {
    T create(T value) throws ServiceException;
    Boolean update(T value) throws ServiceException;
    Boolean delete(T value) throws ServiceException;
    T getById(K id) throws ServiceException;
    List<T> getAll() throws ServiceException;
}
