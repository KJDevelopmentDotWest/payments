package com.epam.jwd.service.comparator.paymentcomparator;

import com.epam.jwd.service.dto.paymentdto.PaymentDTO;

import java.util.Comparator;

public class TimeSortingComparator implements Comparator<PaymentDTO> {

    @Override
    public int compare(PaymentDTO o1, PaymentDTO o2) {
        int dateCompare = o1.getTime().compareTo(o2.getTime());
        int userIdCompare = o1.getUserId().compareTo(o2.getUserId());
        return (dateCompare == 0) ? dateCompare
                : userIdCompare;
    }
}
