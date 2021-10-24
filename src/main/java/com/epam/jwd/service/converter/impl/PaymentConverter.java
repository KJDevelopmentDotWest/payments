package com.epam.jwd.service.converter.impl;

import com.epam.jwd.dao.model.payment.Payment;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.dto.paymentdto.PaymentDTO;

public class PaymentConverter implements Converter<Payment, PaymentDTO, Integer> {
    @Override
    public Payment convert(PaymentDTO value) {
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
    public PaymentDTO convert(Payment value) {
        PaymentDTO paymentDTO = new PaymentDTO(value.getId(),
                value.getUserId(),
                value.getDestinationAddress(),
                value.getPrice(),
                value.isCommitted(),
                value.getTime(),
                value.getName());
        return paymentDTO;
    }
}
