package com.epam.jwd.service.comparator.creditcardcomparator;

import com.epam.jwd.service.dto.creditcarddto.CreditCardDto;

import java.util.Comparator;

public class UserIdSortingComparator implements Comparator<CreditCardDto> {
    @Override
    public int compare(CreditCardDto o1, CreditCardDto o2) {

        int userIdCompare = o1.getUserId().compareTo(o2.getUserId());
        int idCompare = o1.getUserId().compareTo(o2.getUserId());

        return (userIdCompare == 0) ? userIdCompare
                : idCompare;
    }
}
