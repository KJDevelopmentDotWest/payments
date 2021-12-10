package com.epam.jwd.service.converter.api;

import com.epam.jwd.dao.model.Entity;
import com.epam.jwd.service.dto.AbstractDto;

/**
 *
 * @param <T> entity that converter will operate
 * @param <V> abstract dto that converter will operate
 * @param <K> type of id
 */
public interface Converter<T extends Entity<K>, V extends AbstractDto<K>, K> {
    /**
     *
     * @param value dto value to be converted
     * @return converted entity
     */
    T convert(V value);

    /**
     *
     * @param value entity to be converted
     * @return converted dto
     */
    V convert(T value);
}
