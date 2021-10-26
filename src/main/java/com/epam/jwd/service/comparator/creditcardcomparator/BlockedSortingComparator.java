package com.epam.jwd.service.comparator.creditcardcomparator;

import com.epam.jwd.service.dto.creditcarddto.CreditCardDTO;

import java.util.Comparator;

public class BlockedSortingComparator implements Comparator<CreditCardDTO> {
    @Override
    public int compare(CreditCardDTO o1, CreditCardDTO o2) {
        int blockedCompare = o1.getBankAccount().getBlocked().compareTo(o2.getBankAccount().getBlocked());
        int userIdCompare = o1.getUserId().compareTo(o2.getUserId());

        return (blockedCompare == 0) ? blockedCompare
                : userIdCompare;
    }
}
