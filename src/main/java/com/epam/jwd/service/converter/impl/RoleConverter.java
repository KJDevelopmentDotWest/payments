package com.epam.jwd.service.converter.impl;

import com.epam.jwd.dao.model.user.Role;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.dto.userdto.RoleDto;

public class RoleConverter implements Converter<Role, RoleDto, Integer> {
    @Override
    public Role convert(RoleDto value) {
        Role role = new Role(value.getId(),
                value.getName());
        return role;
    }

    @Override
    public RoleDto convert(Role value) {
        RoleDto roleDTO = new RoleDto(value.getId(),
                value.getName());
        return roleDTO;
    }
}
