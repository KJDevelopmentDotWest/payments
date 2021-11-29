package com.epam.jwd.controller.command.impl.showpage;

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

    PaymentService service = new PaymentService();

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {

        logger.info("command " + ShowEditPaymentCommand.class);

        Integer paymentId = Integer.valueOf(request.getParameter("paymentId"));

        PaymentDto payment = null;

        try {
            payment = service.getById(paymentId);
            if (payment.getCommitted()){
                return new CommandResponse(request.getContextPath() + ERROR_PAGE_URL, true);
            }
        } catch (ServiceException e) {
            logger.error(e.getErrorCode());
        }

        request.setAttribute("payment", payment);

        return new CommandResponse(request.getContextPath() + EDIT_PAYMENT_PAGE_URL, false);
    }
}
