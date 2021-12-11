package com.epam.jwd.controller.api;

import com.epam.jwd.service.exception.ServiceException;

@FunctionalInterface
public interface TriFunctionThrowsServiceException<T, U, K, R> {
    /**
     * tri function that throws service exception
     * @param t first argument
     * @param u second argument
     * @param k third argument
     * @return function result
     * @throws ServiceException if function throw ServiceException
     */
    R apply(T t, U u, K k) throws ServiceException;
}
