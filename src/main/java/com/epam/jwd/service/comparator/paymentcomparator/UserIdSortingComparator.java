package com.epam.jwd.service.comparator.paymentcomparator;

import com.epam.jwd.service.dto.paymentdto.PaymentDto;

import java.util.Comparator;

public class UserIdSortingComparator implements Comparator<PaymentDto> {

    @Override
    public int compare(PaymentDto o1, PaymentDto o2) {

        int userIdCompare = o1.getUserId().compareTo(o2.getUserId());
        int nameCompare = o1.getName().compareTo(o2.getName());

        return (userIdCompare == 0) ? userIdCompare
                : nameCompare;
    }
}
