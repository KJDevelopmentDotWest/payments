package com.epam.jwd.service.comparator.creditcardcomparator;

import com.epam.jwd.service.dto.creditcarddto.CreditCardDTO;

import java.util.Comparator;

public class UserIdSortingComparator implements Comparator<CreditCardDTO> {
    @Override
    public int compare(CreditCardDTO o1, CreditCardDTO o2) {

        int userIdCompare = o1.getUserId().compareTo(o2.getUserId());
        int idCompare = o1.getUserId().compareTo(o2.getUserId());

        return (userIdCompare == 0) ? userIdCompare
                : idCompare;
    }
}
