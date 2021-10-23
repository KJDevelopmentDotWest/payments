package com.epam.jwd.service.converter.api;

import com.epam.jwd.dao.model.Entity;
import com.epam.jwd.service.dto.EntityDTO;

public interface Converter<T extends Entity<K>, V extends EntityDTO<K>, K> {
    T convert(V value);
    V convert(T value);
}
