package com.epam.jwd.controller.command.impl.showpage;

import com.epam.jwd.controller.command.ApplicationCommand;
import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import com.epam.jwd.service.dto.paymentdto.PaymentDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShowEditPaymentCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ShowEditPaymentCommand.class);

    private static final String EDIT_PAYMENT_PAGE_URL = "/WEB-INF/jsp/editpayment.jsp";

    private static final String PAYMENT_ID_PARAMETER_NAME = "paymentId";
    private static final String PAYMENT_ATTRIBUTE_NAME = "payment";


    PaymentService service = new PaymentService();

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {

        logger.info("command " + ShowEditPaymentCommand.class);

        Integer paymentId = Integer.valueOf(request.getParameter(PAYMENT_ID_PARAMETER_NAME));

        PaymentDto payment = null;

        try {
            payment = service.getById(paymentId);
            if (payment.getCommitted()){
                return new CommandResponse(request.getContextPath() + ApplicationCommand.SHOW_ERROR_PAGE_URL, true);
            }
        } catch (ServiceException e) {
            logger.error(e.getErrorCode());
        }

        request.setAttribute(PAYMENT_ATTRIBUTE_NAME, payment);

        return new CommandResponse(request.getContextPath() + EDIT_PAYMENT_PAGE_URL, false);
    }
}
