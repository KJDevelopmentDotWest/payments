package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import com.epam.jwd.service.dto.paymentdto.PaymentDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.CreditCardService;
import com.epam.jwd.service.impl.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class CommitPaymentChangesCommand implements Command {

    private static final Logger logger = LogManager.getLogger(CommitPaymentChangesCommand.class);

    private static final String USER_PAYMENTS_PAGE_URL = "/payments?command=show_payments&currentPage=1";
    private static final String SHOW_CHECKOUT_PAGE_URL = "/payments?command=show_checkout";
    private static final String SAVE_ACTION = "save";
    private static final String SAVE_AND_GO_TO_CHECKOUT = "saveAndCheckout";

    PaymentService service = new PaymentService();


    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info(CommitPaymentChangesCommand.class);
        System.out.println(CommitPaymentChangesCommand.class);
        Integer paymentId = Integer.valueOf(request.getParameter("paymentId"));
        try {
            PaymentDto payment = service.getById(paymentId);
            return updatePayment(payment, request);
        } catch (ServiceException e) {
            logger.error(e.getErrorCode());
        }
        return new CommandResponse(request.getContextPath() + ERROR_PAGE_URL, true);
    }

    private CommandResponse updatePayment(PaymentDto paymentDto, HttpServletRequest request) throws ServiceException{

        paymentDto.setDestinationAddress(request.getParameter("destination"));
        paymentDto.setPrice(Long.valueOf(request.getParameter("price")));
        paymentDto.setName(request.getParameter("name"));
        if (Objects.equals(request.getParameter("action"), SAVE_AND_GO_TO_CHECKOUT)){
            service.update(paymentDto);
            request.setAttribute("payment", paymentDto);
            request.setAttribute("creditcards",
                    new CreditCardService().getByUserId(paymentDto.getUserId()));
            return new CommandResponse(request.getContextPath() + SHOW_CHECKOUT_PAGE_URL, false);
        }
        service.update(paymentDto);
        return new CommandResponse(request.getContextPath() + USER_PAYMENTS_PAGE_URL, true);
    }
}
