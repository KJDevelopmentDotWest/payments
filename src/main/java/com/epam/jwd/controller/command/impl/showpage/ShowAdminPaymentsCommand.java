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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShowAdminPaymentsCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ShowAdminPaymentsCommand.class);

    private static final String ADMIN_PAYMENTS_PAGE_URL = "/WEB-INF/jsp/adminpayments.jsp";
    private static final Integer MAX_ITEMS_IN_PAGE = 5;

    private final PaymentService service = new PaymentService();

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("command " + ShowAdminPaymentsCommand.class);

        Integer pageNumber = Integer.valueOf(!Objects.isNull(request.getParameter("currentPage")) ? request.getParameter("currentPage") : "1");
        Integer lastPage = getLastPage(request);
        List<PaymentDto> payments;
        List<PaymentDto> allPayments = new ArrayList<>();

        if (pageNumber > lastPage){
            pageNumber = 1;
        }

        try {
            allPayments = service.getAll();
        } catch (ServiceException e) {
            logger.error(e.getErrorCode());
        }

        if (!allPayments.isEmpty()){
            try {
                payments = allPayments.subList(
                        (pageNumber - 1) * MAX_ITEMS_IN_PAGE,
                        pageNumber * MAX_ITEMS_IN_PAGE);

            } catch (IndexOutOfBoundsException e){
                payments = allPayments.subList(
                        (pageNumber - 1) * MAX_ITEMS_IN_PAGE,
                        allPayments.size() - 1);
                logger.error(e);
            }
        } else {
            payments = new ArrayList<>();
        }

        request.setAttribute("payments", payments);
        request.setAttribute("currentPage", pageNumber);

        return new CommandResponse(request.getContextPath() + ADMIN_PAYMENTS_PAGE_URL, false);
    }

    private Integer getLastPage(HttpServletRequest request){
        Integer maxPage;
        if (Objects.isNull(request.getAttribute("lastPage"))){
            Integer paymentsAmount;
            paymentsAmount = service.getAmount();
            if (Double.compare(paymentsAmount / MAX_ITEMS_IN_PAGE.doubleValue(), paymentsAmount / MAX_ITEMS_IN_PAGE) == 0){
                maxPage = paymentsAmount / MAX_ITEMS_IN_PAGE;
            } else {
                maxPage = paymentsAmount / MAX_ITEMS_IN_PAGE + 1;
            }
            request.setAttribute("lastPage", maxPage);
        } else {
            maxPage = (Integer) request.getAttribute("lastPage");
        }
        return maxPage;
    }
}
