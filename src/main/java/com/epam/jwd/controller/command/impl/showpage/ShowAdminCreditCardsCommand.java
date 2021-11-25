package com.epam.jwd.controller.command.impl.showpage;

import com.epam.jwd.controller.api.BiFunctionThrowsServiceException;
import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import com.epam.jwd.service.dto.creditcarddto.CreditCardDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.CreditCardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Objects;

public class ShowAdminCreditCardsCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ShowAdminPaymentsCommand.class);

    private static final String ADMIN_PAYMENTS_PAGE_URL = "/WEB-INF/jsp/admincreditcards.jsp";
    private static final Integer MAX_ITEMS_IN_PAGE = 5;

    private final CreditCardService service = new CreditCardService();

    private static final String KEY_ORDER_BY_ID = "id";
    private static final String KEY_ORDER_BY_USER_ID = "userId";
    private static final String KEY_ORDER_BY_NAME = "name";
    private static final String KEY_ORDER_BY_NUMBER = "number";
    private static final String KEY_ORDER_BY_EXPIRE_DATE = "date";
    private static final String KEY_ORDER_BY_BALANCE = "balance";
    private static final String KEY_ORDER_BY_STATE = "state";

    private final Map<String, BiFunctionThrowsServiceException<Integer, Integer, List<CreditCardDto>>> orderByKeyToMethodMap;

    public ShowAdminCreditCardsCommand(){
        orderByKeyToMethodMap = new HashMap<>();
        orderByKeyToMethodMap.put(KEY_ORDER_BY_ID, service::getSortedByIdWithinRange);
        orderByKeyToMethodMap.put(KEY_ORDER_BY_USER_ID, service::getSortedByUserIdWithinRange);
        orderByKeyToMethodMap.put(KEY_ORDER_BY_NAME, service::getSortedByNameWithinRange);
        orderByKeyToMethodMap.put(KEY_ORDER_BY_NUMBER, service::getSortedByNumberWithinRange);
        orderByKeyToMethodMap.put(KEY_ORDER_BY_EXPIRE_DATE, service::getSortedByExpireDateWithinRange);
        orderByKeyToMethodMap.put(KEY_ORDER_BY_BALANCE, service::getSortedByBalanceWithinRange);
        orderByKeyToMethodMap.put(KEY_ORDER_BY_STATE, service::getSortedByStateWithinRange);
    }

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("command " + ShowAdminCreditCardsCommand.class);

        Integer pageNumber = Integer.valueOf(!Objects.isNull(request.getParameter("currentPage")) ? request.getParameter("currentPage") : "1");
        Integer lastPage = getLastPage(request);
        List<CreditCardDto> creditCardDto;

        if (pageNumber > lastPage){
            pageNumber = 1;
        }

        String sortBy = request.getParameter("sortBy");

        try {
            creditCardDto = orderByKeyToMethodMap.getOrDefault(sortBy, service::getSortedByIdWithinRange)
                    .apply(MAX_ITEMS_IN_PAGE, (pageNumber -1) * MAX_ITEMS_IN_PAGE);
        } catch (ServiceException e) {
            logger.error(e.getErrorCode());
            creditCardDto = new ArrayList<>();
        }


        request.setAttribute("creditcards", creditCardDto);
        request.setAttribute("currentPage", pageNumber);
        request.setAttribute("sortBy", sortBy);

        return new CommandResponse(request.getContextPath() + ADMIN_PAYMENTS_PAGE_URL, false);
    }

    private Integer getLastPage(HttpServletRequest request){
        Integer maxPage;
        if (Objects.isNull(request.getAttribute("lastPage"))){
            Integer creditCardsAmount;
            creditCardsAmount = service.getAmount();
            if (Double.compare(creditCardsAmount / MAX_ITEMS_IN_PAGE.doubleValue(), creditCardsAmount / MAX_ITEMS_IN_PAGE) == 0){
                maxPage = creditCardsAmount / MAX_ITEMS_IN_PAGE;
            } else {
                maxPage = creditCardsAmount / MAX_ITEMS_IN_PAGE + 1;
            }
            request.setAttribute("lastPage", maxPage);
        } else {
            maxPage = (Integer) request.getAttribute("lastPage");
        }
        return maxPage;
    }
}
