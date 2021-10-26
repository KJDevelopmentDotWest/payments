package com.epam.jwd.service.comparator.usercomparator;

import com.epam.jwd.service.dto.userdto.UserDTO;

import java.util.Comparator;

public class RoleSortingComparator implements Comparator<UserDTO> {
    @Override
    public int compare(UserDTO o1, UserDTO o2) {
        int roleCompare = o1.getAccount().getRoleId().compareTo(o2.getAccount().getRoleId());
        int idCompare = o1.getId().compareTo(o2.getId());

        return (roleCompare == 0) ? roleCompare
                : idCompare;
    }
}
