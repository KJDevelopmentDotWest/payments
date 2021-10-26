package com.epam.jwd.service.comparator.creditcardcomparator;

import com.epam.jwd.service.dto.creditcarddto.CreditCardDto;

import java.util.Comparator;

public class BalanceSortingComparator implements Comparator<CreditCardDto> {
    @Override
    public int compare(CreditCardDto o1, CreditCardDto o2) {
        int balanceCompare = o1.getBankAccount().getBalance().compareTo(o2.getBankAccount().getBalance());
        int userIdCompare = o1.getUserId().compareTo(o2.getUserId());

        return (balanceCompare == 0) ? balanceCompare
                : userIdCompare;
    }
}
