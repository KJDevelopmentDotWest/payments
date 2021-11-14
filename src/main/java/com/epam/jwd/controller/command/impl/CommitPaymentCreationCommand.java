package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import com.epam.jwd.service.dto.paymentdto.PaymentDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.Objects;

public class CommitPaymentCreationCommand implements Command {

    private static final Logger logger = LogManager.getLogger(CommitPaymentCreationCommand.class);

    private static final String USER_PAYMENTS_PAGE_URL = "/payments?command=show_payments&currentPage=1";
    private static final String SAVE_ACTION = "save";
    private static final String SAVE_AND_PAY_ACTION = "saveAndPay";

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();

        PaymentDto paymentDto = new PaymentDto((Integer) session.getAttribute("id"),
                request.getParameter("destination"),
                Long.valueOf(request.getParameter("price")),
                false,
                null,
                request.getParameter("name"));

        if (Objects.equals(request.getParameter("action"), SAVE_AND_PAY_ACTION)){
            paymentDto.setCommitted(true);
            paymentDto.setTime(new Date());
        }

        PaymentService service = new PaymentService();

        try {
            service.create(paymentDto);
        } catch (ServiceException e) {
            logger.error(e.getErrorCode());
        }

        return new CommandResponse(request.getContextPath() + USER_PAYMENTS_PAGE_URL, true);
    }
}
