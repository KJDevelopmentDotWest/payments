package com.epam.jwd.service.comparator.paymentcomparator;

import com.epam.jwd.service.dto.paymentdto.PaymentDto;

import java.util.Comparator;

public class CommittedSortingComparator implements Comparator<PaymentDto> {
    @Override
    public int compare(PaymentDto o1, PaymentDto o2) {

        int committedCompare = o2.getCommitted().compareTo(o1.getCommitted());
        int userIdCompare = o1.getUserId().compareTo(o2.getUserId());

        return (committedCompare == 0) ? committedCompare
                : userIdCompare;
    }
}
