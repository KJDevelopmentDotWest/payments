package com.epam.jwd.service.comparator.paymentcomparator;

import com.epam.jwd.service.dto.paymentdto.PaymentDto;

import java.util.Comparator;
import java.util.Objects;

public class TimeSortingComparator implements Comparator<PaymentDto> {

    @Override
    public int compare(PaymentDto o1, PaymentDto o2) {
        if (Objects.isNull(o1.getTime()) && Objects.isNull(o2.getTime())){
            return 0;
        }
        if (Objects.isNull(o1.getTime()) ){
            return -1;
        }
        if (Objects.isNull(o2.getTime()) ){
            return 1;
        }
        int dateCompare = o1.getTime().compareTo(o2.getTime());
        int userIdCompare = o1.getUserId().compareTo(o2.getUserId());
        return (dateCompare == 0) ? dateCompare
                : userIdCompare;
    }
}
