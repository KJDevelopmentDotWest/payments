package com.epam.jwd.controller.command.impl.showpage;

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

public class ShowCheckoutCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ShowCheckoutCommand.class);

    private static final String CHECKOUT_PAGE_URL = "/WEB-INF/jsp/paymentcheckout.jsp";

    private static final String PAYMENT_ID_PARAMETER_NAME = "paymentId";
    private static final String PAYMENT_PARAMETER_NAME = "payment";
    private static final String CREDIT_CARDS_PARAMETER_NAME = "creditcards";

    PaymentService service = new PaymentService();

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("command " + ShowCheckoutCommand.class);

        if (Objects.isNull(request.getAttribute(PAYMENT_PARAMETER_NAME))){
            Integer paymentId = Integer.valueOf(request.getParameter(PAYMENT_ID_PARAMETER_NAME));
            try {
                PaymentDto payment = service.getById(paymentId);
                request.setAttribute(PAYMENT_PARAMETER_NAME, payment);
            } catch (ServiceException e) {
                logger.error(e.getErrorCode());
                return new CommandResponse(request.getContextPath() + ApplicationCommand.SHOW_ERROR_PAGE_URL, true);
            }
        }

        try {
            request.setAttribute(CREDIT_CARDS_PARAMETER_NAME,
                    new CreditCardService().getByUserId((Integer) request.getSession().getAttribute(ID_ATTRIBUTE_NAME)));
        } catch (ServiceException e){
            logger.error(e);
            if (e.getErrorCode() == ExceptionCode.CREDIT_CARD_IS_NOT_FOUND_EXCEPTION_CODE){
                request.setAttribute(CREDIT_CARDS_PARAMETER_NAME,
                        null);
            } else {
                return new CommandResponse(request.getContextPath() + ApplicationCommand.SHOW_ERROR_PAGE_URL, true);
            }
        }
        return new CommandResponse(request.getContextPath() + CHECKOUT_PAGE_URL, false);
    }
}
