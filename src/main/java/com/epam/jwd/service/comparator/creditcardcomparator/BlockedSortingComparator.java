package com.epam.jwd.service.comparator.creditcardcomparator;

import com.epam.jwd.service.dto.creditcarddto.CreditCardDto;

import java.util.Comparator;

public class BlockedSortingComparator implements Comparator<CreditCardDto> {
    @Override
    public int compare(CreditCardDto o1, CreditCardDto o2) {
        int blockedCompare = o1.getBankAccount().getBlocked().compareTo(o2.getBankAccount().getBlocked());
        int userIdCompare = o1.getUserId().compareTo(o2.getUserId());

        return (blockedCompare == 0) ? blockedCompare
                : userIdCompare;
    }
}
