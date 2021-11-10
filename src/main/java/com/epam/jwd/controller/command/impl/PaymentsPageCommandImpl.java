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

import java.util.List;
import java.util.Objects;

public class PaymentsPageCommandImpl implements Command {

    private static final Logger logger = LogManager.getLogger(PaymentsPageCommandImpl.class);

    private static final String USER_PAYMENTS_PAGE_URL = "/jsp/payments.jsp";
    private static final Integer MAX_ITEMS_IN_PAGE = 5;

    private final PaymentService service = new PaymentService();

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("id");

        logger.info("" + PaymentsPageCommandImpl.class);
        Integer pageNumber = Integer.valueOf(request.getParameter("currentPage"));
        logger.info(pageNumber);
        List<PaymentDto> payments;
        try {
            payments = service.getByUserIdWithinRange(userId,
                    MAX_ITEMS_IN_PAGE,
                    (pageNumber -1) * MAX_ITEMS_IN_PAGE);
            logger.info("worked");
        } catch (ServiceException e) {
            logger.error(e);
            payments = null;
        }
        request.setAttribute("payments", payments);
        request.setAttribute("currentPage", pageNumber);

        if (Objects.isNull(request.getAttribute("maxPage"))){
            Integer paymentsAmount = null;
            try {
                paymentsAmount = service.getAmountWithUserId(userId);
            } catch (ServiceException e) {
                paymentsAmount = 0;
                logger.error(e);
            }
            Integer maxPage;
            if (paymentsAmount / MAX_ITEMS_IN_PAGE.doubleValue() == paymentsAmount / MAX_ITEMS_IN_PAGE){
                maxPage = paymentsAmount/MAX_ITEMS_IN_PAGE;
            } else {
                maxPage = paymentsAmount/MAX_ITEMS_IN_PAGE + 1;
            }
            request.setAttribute("maxPage", maxPage);
        }

        return new CommandResponse(request.getContextPath() + USER_PAYMENTS_PAGE_URL, false);
    }
}
