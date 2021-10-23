package com.epam.jwd.service.converter.impl;

import com.epam.jwd.dao.model.user.Role;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.dto.userdto.RoleDTO;

public class RoleConverter implements Converter<Role, RoleDTO, Integer> {
    @Override
    public Role convert(RoleDTO value) {
        Role role = new Role(value.getName());
        return role;
    }

    @Override
    public RoleDTO convert(Role value) {
        RoleDTO roleDTO = new RoleDTO(value.getId(), value.getName());
        return roleDTO;
    }
}
