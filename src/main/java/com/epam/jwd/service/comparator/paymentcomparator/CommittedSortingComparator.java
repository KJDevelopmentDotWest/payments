package com.epam.jwd.service.comparator.paymentcomparator;

import com.epam.jwd.service.dto.paymentdto.PaymentDTO;

import java.util.Comparator;

public class CommittedSortingComparator implements Comparator<PaymentDTO> {
    @Override
    public int compare(PaymentDTO o1, PaymentDTO o2) {

        int committedCompare = o2.getCommitted().compareTo(o1.getCommitted());
        int userIdCompare = o1.getUserId().compareTo(o2.getUserId());

        return (committedCompare == 0) ? committedCompare
                : userIdCompare;
    }
}
