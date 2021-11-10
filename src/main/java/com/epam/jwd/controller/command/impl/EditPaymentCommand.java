package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import com.epam.jwd.service.dto.paymentdto.PaymentDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EditPaymentCommand implements Command {

    private static final Logger logger = LogManager.getLogger(EditPaymentCommand.class);

    private static final String EDIT_PAYMENT_PAGE_URL = "/jsp/editpayment.jsp";
    private static final String ERROR_PAGE_URL = "/jsp/errorpage.jsp";

    PaymentService service = new PaymentService();

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {

        Integer paymentId = Integer.valueOf(request.getParameter("paymentId"));

        PaymentDto payment = null;

        try {
            payment = service.getById(paymentId);
            if (payment.getCommitted()){
                return new CommandResponse(request.getContextPath() + ERROR_PAGE_URL, true);
            }
        } catch (ServiceException e) {
            logger.error(e);
        }

        request.setAttribute("payment", payment);

        return new CommandResponse(request.getContextPath() + EDIT_PAYMENT_PAGE_URL, false);
    }
}
