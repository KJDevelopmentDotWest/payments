package com.epam.jwd.service.converter.impl;

import com.epam.jwd.dao.model.user.Account;
import com.epam.jwd.dao.model.user.User;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.dto.userdto.AccountDTO;
import com.epam.jwd.service.dto.userdto.UserDTO;

public class UserConverter implements Converter<User, UserDTO, Integer> {
    @Override
    public User convert(UserDTO value) {
        User user = new User(value.getLogin(),
                value.getPassword(),
                convertAccount(value.getAccount()));
        return user;
    }

    @Override
    public UserDTO convert(User value) {
        UserDTO userDTO = new UserDTO(value.getId(),
                value.getLogin(),
                value.getPassword(),
                convertAccount(value.getAccount()));
        return userDTO;
    }

    private Account convertAccount(AccountDTO accountDTO){
        Account account = new Account(accountDTO.getName(),
                accountDTO.getSurname(),
                accountDTO.getRoleId());
        return account;
    }

    private AccountDTO convertAccount(Account account){
        AccountDTO accountDTO = new AccountDTO(account.getId(),
                account.getName(),
                account.getSurname(),
                account.getRoleId());
        return accountDTO;
    }
}
