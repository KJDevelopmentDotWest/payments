package com.epam.jwd.service.converter.impl;

import com.epam.jwd.dao.model.user.Account;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.dto.userdto.AccountDto;

public class AccountConverter implements Converter<Account, AccountDto, Integer> {

    @Override
    public Account convert(AccountDto accountDTO){
        Account account = new Account(accountDTO.getId(),
                accountDTO.getName(),
                accountDTO.getSurname(),
                accountDTO.getProfilePictureIId());
        return account;
    }

    @Override
    public AccountDto convert(Account account){
        AccountDto accountDTO = new AccountDto(account.getId(),
                account.getName(),
                account.getSurname(),
                account.getProfilePictureId());
        return accountDTO;
    }
}
