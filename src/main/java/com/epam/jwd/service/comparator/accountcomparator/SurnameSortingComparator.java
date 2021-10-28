package com.epam.jwd.service.comparator.accountcomparator;

import com.epam.jwd.service.dto.userdto.AccountDto;

import java.util.Comparator;

public class SurnameSortingComparator implements Comparator<AccountDto> {
    @Override
    public int compare(AccountDto o1, AccountDto o2) {

        int surnameCompare = o1.getSurname().compareTo(o2.getSurname());
        int idCompare = o1.getId().compareTo(o2.getId());

        return (surnameCompare == 0) ? surnameCompare
                : idCompare;
    }
}
