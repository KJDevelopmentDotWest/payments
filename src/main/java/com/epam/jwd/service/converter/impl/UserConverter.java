package com.epam.jwd.service.converter.impl;

import com.epam.jwd.dao.model.user.User;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.dto.userdto.UserDto;

public class UserConverter implements Converter<User, UserDto, java.lang.Integer> {
    @Override
    public User convert(UserDto value) {
        return new User(value.getId(),
                value.getLogin(),
                value.getPassword(),
                value.getAccountId(),
                value.getActive(),
                value.getRole());
    }

    @Override
    public UserDto convert(User value) {
        return new UserDto(value.getId(),
                value.getLogin(),
                value.getPassword(),
                value.getAccountId(),
                value.getActive(),
                value.getRole());
    }
}
