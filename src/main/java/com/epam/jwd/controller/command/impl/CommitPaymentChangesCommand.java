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

import java.util.Date;
import java.util.Objects;

public class CommitPaymentChangesCommand implements Command {

    private static final Logger logger = LogManager.getLogger(EditPaymentCommand.class);

    private static final String USER_PAYMENTS_PAGE_URL = "/payments?command=payments&currentPage=1";
    private static final String SAVE_ACTION = "save";
    private static final String SAVE_AND_PAY_ACTION = "saveAndPay";

    PaymentService service = new PaymentService();


    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {
        Integer paymentId = Integer.valueOf(request.getParameter("paymentId"));
        PaymentDto payment;
        try {
            payment = service.getById(paymentId);
        } catch (ServiceException e) {
            payment = null;
            logger.error(e.getErrorCode());
        }
        updatePayment(payment, request);
        return new CommandResponse(request.getContextPath() + USER_PAYMENTS_PAGE_URL, true);
    }

    private void updatePayment(PaymentDto paymentDto, HttpServletRequest request){
        PaymentDto result = new PaymentDto(paymentDto.getId(),
                paymentDto.getUserId(),
                request.getParameter("destination"),
                Integer.valueOf(request.getParameter("price")),
                false,
                null,
                request.getParameter("name"));
        if (Objects.equals(request.getParameter("action"), SAVE_AND_PAY_ACTION)){
            result.setCommitted(true);
            result.setTime(new Date());
            try {
                service.update(result);
            } catch (ServiceException e) {
                logger.info(e.getErrorCode());
            }
        } else {
            if (!result.equals(paymentDto)){
                try {
                    service.update(result);
                } catch (ServiceException e) {
                    logger.info(e.getErrorCode());
                }
            }
        }
    }
}
