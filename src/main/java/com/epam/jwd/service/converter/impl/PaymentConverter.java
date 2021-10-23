package com.epam.jwd.service.converter.impl;

import com.epam.jwd.dao.model.payment.Payment;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.dto.paymentdto.PaymentDTO;

public class PaymentConverter implements Converter<Payment, PaymentDTO, Integer> {
    @Override
    public Payment convert(PaymentDTO value) {
        return null;
    }

    @Override
    public PaymentDTO convert(Payment value) {
        return null;
    }
}
