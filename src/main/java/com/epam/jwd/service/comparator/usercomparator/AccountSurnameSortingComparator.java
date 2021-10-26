package com.epam.jwd.service.comparator.usercomparator;

import com.epam.jwd.service.dto.userdto.UserDto;

import java.util.Comparator;

public class AccountSurnameSortingComparator implements Comparator<UserDto> {
    @Override
    public int compare(UserDto o1, UserDto o2) {

        int surnameCompare = o1.getAccount().getSurname().compareTo(o2.getAccount().getSurname());
        int idCompare = o1.getId().compareTo(o2.getId());

        return (surnameCompare == 0) ? surnameCompare
                : idCompare;
    }
}
