package com.epam.jwd.controller.command.impl.action;

import com.epam.jwd.controller.command.ApplicationCommand;
import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeletePaymentCommand implements Command {

    private static final Logger logger = LogManager.getLogger(DeletePaymentCommand.class);

    private static final String PAYMENT_ID_PARAMETER_NAME = "paymentId";

    private static final String SHOW_PAYMENTS_PAGE_URL = "/payments?command=show_payments&currentPage=1";

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {

        Integer paymentId;

        try {
            paymentId = Integer.valueOf(request.getParameter(PAYMENT_ID_PARAMETER_NAME));
        } catch (NumberFormatException e) {
            logger.error(e);
            return new CommandResponse(request.getContextPath() + ApplicationCommand.SHOW_ERROR_PAGE_URL, true);
        }

        try {
            PaymentService service = new PaymentService();
            service.delete(service.getById(paymentId));
        } catch (ServiceException e) {
            logger.error(e.getErrorCode());
        }

        return new CommandResponse(request.getContextPath() + SHOW_PAYMENTS_PAGE_URL, true);
    }
}
