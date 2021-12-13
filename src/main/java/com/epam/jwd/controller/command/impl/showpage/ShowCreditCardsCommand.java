package com.epam.jwd.controller.command.impl.showpage;

import com.epam.jwd.controller.api.TriFunctionThrowsServiceException;
import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import com.epam.jwd.service.dto.creditcarddto.CreditCardDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.CreditCardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class ShowCreditCardsCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ShowCreditCardsCommand.class);

    private static final String USER_CREDIT_CARDS_PAGE_URL = "/WEB-INF/jsp/creditcards.jsp";
    private static final Integer MAX_ITEMS_IN_PAGE = 5;

    private static final String CREDIT_CARDS_ATTRIBUTE_NAME = "creditcards";
    private static final String STRING_NUM_1 = "1";

    CreditCardService service = new CreditCardService();

    private static final String KEY_ORDER_BY_NAME = "name";
    private static final String KEY_ORDER_BY_NUMBER = "number";
    private static final String KEY_ORDER_BY_EXPIRE_DATE = "date";
    private static final String KEY_ORDER_BY_BALANCE = "balance";
    private static final String KEY_ORDER_BY_STATE = "state";

    private final Map<String, TriFunctionThrowsServiceException<Integer, Integer, Integer, List<CreditCardDto>>> orderByKeyToMethodMap;

    public ShowCreditCardsCommand() {
        this.orderByKeyToMethodMap = new HashMap<>();
        orderByKeyToMethodMap.put(KEY_ORDER_BY_NAME, service::getByUserIdSortedByNameWithinRange);
        orderByKeyToMethodMap.put(KEY_ORDER_BY_NUMBER, service::getByUserIdSortedByNumberWithinRange);
        orderByKeyToMethodMap.put(KEY_ORDER_BY_EXPIRE_DATE, service::getByUserIdSortedByExpireDateWithinRange);
        orderByKeyToMethodMap.put(KEY_ORDER_BY_BALANCE, service::getByUserIdSortedByBalanceWithinRange);
        orderByKeyToMethodMap.put(KEY_ORDER_BY_STATE, service::getByUserIdSortedByStateWithinRange);
    }

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("command " + ShowCreditCardsCommand.class);

        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute(ID_ATTRIBUTE_NAME);
        Integer pageNumber = Integer.valueOf(!Objects.isNull(request.getParameter(CURRENT_PAGE_PARAMETER_NAME)) ? request.getParameter(CURRENT_PAGE_PARAMETER_NAME) : STRING_NUM_1);
        Integer lastPage = getLastPage(request, userId);
        List<CreditCardDto> creditCards;

        String sortBy = request.getParameter(SORT_BY_PARAMETER_NAME);

        if (pageNumber > lastPage || pageNumber < 0){
            pageNumber = 1;
        }

        try {
            creditCards = orderByKeyToMethodMap.getOrDefault(sortBy, service::getByUserIdSortedByNameWithinRange)
                    .apply(userId, MAX_ITEMS_IN_PAGE, (pageNumber -1) * MAX_ITEMS_IN_PAGE);
        } catch (ServiceException e) {
            logger.error(e.getErrorCode());
            creditCards = new ArrayList<>();
        }

        request.setAttribute(SORT_BY_PARAMETER_NAME, sortBy);
        request.setAttribute(CREDIT_CARDS_ATTRIBUTE_NAME, creditCards);
        request.setAttribute(CURRENT_PAGE_PARAMETER_NAME, pageNumber);

        return new CommandResponse(request.getContextPath() + USER_CREDIT_CARDS_PAGE_URL, false);
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
