package com.epam.jwd.service.converter.impl;

import com.epam.jwd.dao.model.user.Account;
import com.epam.jwd.dao.model.user.User;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.dto.userdto.AccountDto;
import com.epam.jwd.service.dto.userdto.UserDto;

public class UserConverter implements Converter<User, UserDto, Integer> {
    @Override
    public User convert(UserDto value) {
        User user = new User(value.getId(),
                value.getLogin(),
                value.getPassword(),
                convertAccount(value.getAccount()));
        return user;
    }

    @Override
    public UserDto convert(User value) {
        UserDto userDTO = new UserDto(value.getId(),
                value.getLogin(),
                value.getPassword(),
                convertAccount(value.getAccount()));
        return userDTO;
    }

    private Account convertAccount(AccountDto accountDTO){
        Account account = new Account(accountDTO.getId(),
                accountDTO.getName(),
                accountDTO.getSurname(),
                accountDTO.getRoleId());
        return account;
    }

    private AccountDto convertAccount(Account account){
        AccountDto accountDTO = new AccountDto(account.getId(),
                account.getName(),
                account.getSurname(),
                account.getRoleId());
        return accountDTO;
    }
}
