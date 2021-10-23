package com.epam.jwd.service.converter.impl;

import com.epam.jwd.dao.model.user.Role;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.dto.userdto.RoleDTO;

public class RoleConverter implements Converter<Role, RoleDTO, Integer> {
    @Override
    public Role convert(RoleDTO value) {
        return null;
    }

    @Override
    public RoleDTO convert(Role value) {
        return null;
    }
}
