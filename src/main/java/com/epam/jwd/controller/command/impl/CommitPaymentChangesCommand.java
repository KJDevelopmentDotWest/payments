package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.ApplicationCommand;
import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import com.epam.jwd.service.dto.paymentdto.PaymentDto;
import com.epam.jwd.service.exception.ExceptionCode;
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
    private static final String SAVE_AND_PAY_ACTION = "checkout";

    private static final String PAYMENT_ID_PARAMETER_NAME = "paymentId";
    private static final String DESTINATION_PARAMETER_NAME = "destination";
    private static final String PRICE_PARAMETER_NAME = "price";
    private static final String NAME_PARAMETER_NAME = "name";
    private static final String ACTION_PARAMETER_NAME = "action";
    private static final String PAYMENT_PARAMETER_NAME = "payment";
    private static final String CREDIT_CARDS_PARAMETER_NAME = "creditcards";


    PaymentService service = new PaymentService();

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("command " + CommitPaymentChangesCommand.class);
        Integer paymentId = Integer.valueOf(request.getParameter(PAYMENT_ID_PARAMETER_NAME));
        try {
            PaymentDto payment = service.getById(paymentId);
            return updatePayment(payment, request);
        } catch (ServiceException e) {
            logger.error(e.getErrorCode());
        }
        return new CommandResponse(request.getContextPath() + ApplicationCommand.SHOW_ERROR_PAGE_URL, true);
    }

    private CommandResponse updatePayment(PaymentDto paymentDto, HttpServletRequest request) throws ServiceException{

        paymentDto.setDestinationAddress(request.getParameter(DESTINATION_PARAMETER_NAME));
        paymentDto.setPrice(Long.valueOf(request.getParameter(PRICE_PARAMETER_NAME)));
        paymentDto.setName(request.getParameter(NAME_PARAMETER_NAME));
        service.update(paymentDto);
        if (Objects.equals(request.getParameter(ACTION_PARAMETER_NAME), SAVE_AND_PAY_ACTION)){
            request.setAttribute(PAYMENT_PARAMETER_NAME, paymentDto);
            try {
                request.setAttribute(CREDIT_CARDS_PARAMETER_NAME,
                        new CreditCardService().getByUserId(paymentDto.getUserId()));
            } catch (ServiceException e){
                logger.error(e);
                if (e.getErrorCode() == ExceptionCode.CREDIT_CARD_IS_NOT_FOUND_EXCEPTION_CODE){
                    request.setAttribute(CREDIT_CARDS_PARAMETER_NAME,
                            null);
                } else {
                    return new CommandResponse(request.getContextPath() + ApplicationCommand.SHOW_ERROR_PAGE_URL, true);
                }
            }
            return new CommandResponse(request.getContextPath() + SHOW_CHECKOUT_PAGE_URL, false);
        }
        return new CommandResponse(request.getContextPath() + USER_PAYMENTS_PAGE_URL, true);
    }
}
