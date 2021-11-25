package com.epam.jwd.controller.api;

import com.epam.jwd.service.exception.ServiceException;

@FunctionalInterface
public interface BiFunctionThrowsServiceException<T, U, R>  {
    R apply(T t, U u) throws ServiceException;
}
