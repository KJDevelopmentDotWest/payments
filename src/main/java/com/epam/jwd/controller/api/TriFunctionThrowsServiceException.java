package com.epam.jwd.controller.api;

import com.epam.jwd.service.exception.ServiceException;

@FunctionalInterface
public interface TriFunctionThrowsServiceException<T, U, K, R> {
    R apply(T t, U u, K k) throws ServiceException;
}
