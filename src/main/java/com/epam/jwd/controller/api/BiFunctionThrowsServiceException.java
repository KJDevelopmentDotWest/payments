package com.epam.jwd.controller.api;

import com.epam.jwd.service.exception.ServiceException;

import java.util.function.BiFunction;

@FunctionalInterface
public interface BiFunctionThrowsServiceException<T, U, R>  {
    /**
     * bi function that throws service exception
     * @param t first argument
     * @param u second argument
     * @return function result
     * @throws ServiceException if function throw ServiceException
     */
    R apply(T t, U u) throws ServiceException;
}
