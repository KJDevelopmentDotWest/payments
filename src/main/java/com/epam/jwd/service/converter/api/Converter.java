package com.epam.jwd.service.converter.api;

import com.epam.jwd.dao.model.Entity;
import com.epam.jwd.service.dto.AbstractDTO;

public interface Converter<T extends Entity<K>, V extends AbstractDTO<K>, K> {
    T convert(V value);
    V convert(T value);
}
