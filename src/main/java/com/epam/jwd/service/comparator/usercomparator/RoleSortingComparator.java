package com.epam.jwd.service.comparator.usercomparator;

import com.epam.jwd.service.dto.userdto.UserDto;

import java.util.Comparator;

public class RoleSortingComparator implements Comparator<UserDto> {
    @Override
    public int compare(UserDto o1, UserDto o2) {

        int roleCompare = o1.getRole().getId().compareTo(o2.getRole().getId());
        int idCompare = o1.getId().compareTo(o2.getId());

        return (roleCompare == 0) ? roleCompare
                : idCompare;
    }
}
