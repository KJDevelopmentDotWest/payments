package com.epam.jwd.service.converter.impl;

import com.epam.jwd.dao.model.payment.Payment;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.dto.paymentdto.PaymentDto;

public class PaymentConverter implements Converter<Payment, PaymentDto, Integer> {
    @Override
    public Payment convert(PaymentDto value) {
        Payment payment = new Payment(value.getId(),
                value.getUserId(),
                value.getDestinationAddress(),
                value.getPrice(),
                value.getCommitted(),
                value.getTime(),
                value.getName());
        return payment;
    }

    @Override
    public PaymentDto convert(Payment value) {
        PaymentDto paymentDTO = new PaymentDto(value.getId(),
                value.getUserId(),
                value.getDestinationAddress(),
                value.getPrice(),
                value.isCommitted(),
                value.getTime(),
                value.getName());
        return paymentDTO;
    }
}
