package com.epam.jwd.controller.command.impl.showpage;

import com.epam.jwd.controller.api.BiFunctionThrowsServiceException;
import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import com.epam.jwd.service.dto.paymentdto.PaymentDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Objects;

public class ShowAdminPaymentsCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ShowAdminPaymentsCommand.class);

    private static final String ADMIN_PAYMENTS_PAGE_URL = "/WEB-INF/jsp/adminpayments.jsp";
    private static final Integer MAX_ITEMS_IN_PAGE = 5;

    private final PaymentService service = new PaymentService();

    private static final String KEY_ORDER_BY_ID = "id";
    private static final String KEY_ORDER_BY_USER_ID = "userId";
    private static final String KEY_ORDER_BY_NAME = "name";
    private static final String KEY_ORDER_BY_PRICE = "price";
    private static final String KEY_ORDER_BY_DESTINATION_ADDRESS = "destination";
    private static final String KEY_ORDER_BY_TIME = "time";
    private static final String KEY_ORDER_BY_COMMITTED = "committed";

    private final Map<String, BiFunctionThrowsServiceException<Integer, Integer, List<PaymentDto>>> orderByKeyToMethodMap;

    public ShowAdminPaymentsCommand(){
        orderByKeyToMethodMap = new HashMap<>();
        orderByKeyToMethodMap.put(KEY_ORDER_BY_ID, service::getSortedByIdWithinRange);
        orderByKeyToMethodMap.put(KEY_ORDER_BY_USER_ID, service::getSortedByUserIdWithinRange);
        orderByKeyToMethodMap.put(KEY_ORDER_BY_NAME, service::getSortedByNameWithinRange);
        orderByKeyToMethodMap.put(KEY_ORDER_BY_PRICE, service::getSortedByPriceWithinRange);
        orderByKeyToMethodMap.put(KEY_ORDER_BY_DESTINATION_ADDRESS, service::getSortedByDestinationAddressWithinRange);
        orderByKeyToMethodMap.put(KEY_ORDER_BY_TIME, service::getSortedByTimeWithinRange);
        orderByKeyToMethodMap.put(KEY_ORDER_BY_COMMITTED, service::getSortedByCommittedWithinRange);
    }

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("command " + ShowAdminPaymentsCommand.class);

        Integer pageNumber = Integer.valueOf(!Objects.isNull(request.getParameter("currentPage")) ? request.getParameter("currentPage") : "1");
        Integer lastPage = getLastPage(request);
        List<PaymentDto> payments;

        if (pageNumber > lastPage){
            pageNumber = 1;
        }

        String sortBy = request.getParameter("sortBy");

        try {
            payments = orderByKeyToMethodMap.getOrDefault(sortBy, service::getSortedByIdWithinRange)
                    .apply(MAX_ITEMS_IN_PAGE, (pageNumber -1) * MAX_ITEMS_IN_PAGE);
        } catch (ServiceException e) {
            logger.error(e.getErrorCode());
            payments = new ArrayList<>();
        }


        request.setAttribute("payments", payments);
        request.setAttribute("currentPage", pageNumber);
        request.setAttribute("sortBy", sortBy);

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
