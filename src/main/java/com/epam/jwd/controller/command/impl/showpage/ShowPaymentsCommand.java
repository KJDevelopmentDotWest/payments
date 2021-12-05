package com.epam.jwd.controller.command.impl.showpage;

import com.epam.jwd.controller.api.BiFunctionThrowsServiceException;
import com.epam.jwd.controller.api.TriFunctionThrowsServiceException;
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

import java.util.*;

public class ShowPaymentsCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ShowPaymentsCommand.class);

    private static final String USER_PAYMENTS_PAGE_URL = "/WEB-INF/jsp/payments.jsp";
    private static final Integer MAX_ITEMS_IN_PAGE = 5;

    private static final String PAYMENTS_ATTRIBUTE_NAME = "payments";
    private static final String STRING_NUM_1 = "1";

    private final PaymentService service = new PaymentService();

    private static final String KEY_ORDER_BY_NAME = "name";
    private static final String KEY_ORDER_BY_PRICE = "price";
    private static final String KEY_ORDER_BY_DESTINATION_ADDRESS = "destination";
    private static final String KEY_ORDER_BY_TIME = "time";
    private static final String KEY_ORDER_BY_COMMITTED = "committed";

    private final Map<String, TriFunctionThrowsServiceException<Integer, Integer, Integer, List<PaymentDto>>> orderByKeyToMethodMap;

    public ShowPaymentsCommand(){
        orderByKeyToMethodMap = new HashMap<>();
        orderByKeyToMethodMap.put(KEY_ORDER_BY_NAME, service::getByUserIdSortedByNameWithinRange);
        orderByKeyToMethodMap.put(KEY_ORDER_BY_PRICE, service::getByUserIdSortedByPriceWithinRange);
        orderByKeyToMethodMap.put(KEY_ORDER_BY_DESTINATION_ADDRESS, service::getByUserIdSortedByDestinationAddressWithinRange);
        orderByKeyToMethodMap.put(KEY_ORDER_BY_TIME, service::getByUserIdSortedByTimeWithinRange);
        orderByKeyToMethodMap.put(KEY_ORDER_BY_COMMITTED, service::getByUserIdSortedByCommittedWithinRange);
    }

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("command " + ShowPaymentsCommand.class);

        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute(ID_ATTRIBUTE_NAME);
        Integer pageNumber = Integer.valueOf(!Objects.isNull(request.getParameter(CURRENT_PAGE_PARAMETER_NAME)) ? request.getParameter(CURRENT_PAGE_PARAMETER_NAME) : STRING_NUM_1);
        Integer lastPage = getLastPage(request, userId);
        List<PaymentDto> payments;

        String sortBy = request.getParameter(SORT_BY_PARAMETER_NAME);

        if (pageNumber > lastPage){
            pageNumber = 1;
        }

        try {
            payments = orderByKeyToMethodMap.getOrDefault(sortBy, service::getByUserIdSortedByNameWithinRange)
                    .apply(userId, MAX_ITEMS_IN_PAGE, (pageNumber -1) * MAX_ITEMS_IN_PAGE);
        } catch (ServiceException e) {
            logger.error(e.getErrorCode());
            payments = new ArrayList<>();
        }

        request.setAttribute(PAYMENTS_ATTRIBUTE_NAME, payments);
        request.setAttribute(CURRENT_PAGE_PARAMETER_NAME, pageNumber);
        request.setAttribute(SORT_BY_PARAMETER_NAME, sortBy);

        return new CommandResponse(request.getContextPath() + USER_PAYMENTS_PAGE_URL, false);
    }

    private Integer getLastPage(HttpServletRequest request, Integer userId){
        Integer lastPage;
        Integer paymentsAmount;
        try {
            paymentsAmount = service.getAmountWithUserId(userId);
        } catch (ServiceException e) {
            paymentsAmount = 0;
            logger.error(e.getErrorCode());
        }
        if (Double.compare(paymentsAmount / MAX_ITEMS_IN_PAGE.doubleValue(), paymentsAmount / MAX_ITEMS_IN_PAGE) == 0){
            lastPage = paymentsAmount / MAX_ITEMS_IN_PAGE;
        } else {
            lastPage = paymentsAmount / MAX_ITEMS_IN_PAGE + 1;
        }
        request.setAttribute(LAST_PAGE_PARAMETER_NAME, lastPage);
        return lastPage;
    }
}
