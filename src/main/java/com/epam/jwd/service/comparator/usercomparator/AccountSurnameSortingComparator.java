package com.epam.jwd.service.comparator.usercomparator;

import com.epam.jwd.service.dto.userdto.UserDTO;

import java.util.Comparator;

public class AccountSurnameSortingComparator implements Comparator<UserDTO> {
    @Override
    public int compare(UserDTO o1, UserDTO o2) {

        int surnameCompare = o1.getAccount().getSurname().compareTo(o2.getAccount().getSurname());
        int idCompare = o1.getId().compareTo(o2.getId());

        return (surnameCompare == 0) ? surnameCompare
                : idCompare;
    }
}
