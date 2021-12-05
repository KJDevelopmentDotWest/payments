package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import com.epam.jwd.service.dto.creditcarddto.BankAccountDto;
import com.epam.jwd.service.dto.creditcarddto.CreditCardDto;
import com.epam.jwd.service.dto.paymentdto.PaymentDto;
import com.epam.jwd.service.exception.ExceptionCode;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.CreditCardService;
import com.epam.jwd.service.impl.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

public class CheckoutPaymentCommand implements Command {

    private static final Logger logger = LogManager.getLogger(CheckoutPaymentCommand.class);

    private static final String SHOW_PAYMENTS_PAGE_URL = "/payments?command=show_payments&currentPage=1";

    private static final String CREDIT_CARD_ID_PARAMETER_NAME = "creditCardId";
    private static final String PAYMENT_ID_PARAMETER_NAME = "paymentId";

    PaymentService paymentService = new PaymentService();
    CreditCardService creditCardService = new CreditCardService();

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("command " + CheckoutPaymentCommand.class);

        Integer paymentId = Integer.valueOf(request.getParameter(PAYMENT_ID_PARAMETER_NAME));
        Integer creditCardId = Integer.valueOf(request.getParameter(CREDIT_CARD_ID_PARAMETER_NAME));

        try {
            PaymentDto payment = paymentService.getById(paymentId);
            CreditCardDto creditCardDto = creditCardService.getById(creditCardId);
            payment.setCommitted(true);
            payment.setTime(new Date());
            BankAccountDto bankAccount = creditCardDto.getBankAccount();
            bankAccount.setBalance(bankAccount.getBalance() - payment.getPrice());
            paymentService.update(payment);
            creditCardService.update(creditCardDto);
        } catch (ServiceException e) {
            logger.error(e.getErrorCode());
        }

        return new CommandResponse(request.getContextPath() + SHOW_PAYMENTS_PAGE_URL, true);
    }
}
