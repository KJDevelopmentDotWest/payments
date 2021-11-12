package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import com.epam.jwd.dao.model.creditcard.CreditCard;
import com.epam.jwd.service.dto.creditcarddto.CreditCardDto;
import com.epam.jwd.service.exception.ExceptionCode;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.CreditCardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreditCardsCommand implements Command {

    private static final Logger logger = LogManager.getLogger(PaymentsCommand.class);

    private static final String USER_CREDIT_CARDS_PAGE_URL = "/jsp/creditcards.jsp";
    private static final Integer MAX_ITEMS_IN_PAGE = 5;

    CreditCardService service = new CreditCardService();

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("command " + CreditCardsCommand.class);

        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("id");
        Integer pageNumber = Integer.valueOf(request.getParameter("currentPage"));
        Integer lastPage = getLastPage(request, userId);
        List<CreditCardDto> creditCards;

        if (pageNumber > lastPage){
            pageNumber = 1;
        }

        try {
            creditCards = service.getByUserIdWithinRange(userId,
                    MAX_ITEMS_IN_PAGE,
                    (pageNumber -1) * MAX_ITEMS_IN_PAGE);
        } catch (ServiceException e) {
            if (e.getErrorCode() == ExceptionCode.REPOSITORY_IS_EMPTY_EXCEPTION_CODE){
                logger.info("caught");
            }
            logger.error(e.getErrorCode());
            creditCards = new ArrayList<>();
        }

        request.setAttribute("creditcards", creditCards);
        request.setAttribute("currentPage", pageNumber);

        return new CommandResponse(request.getContextPath() + USER_CREDIT_CARDS_PAGE_URL, false);
    }

    private Integer getLastPage(HttpServletRequest request, Integer userId){
        Integer maxPage;
        if (Objects.isNull(request.getAttribute("lastPage"))){
            Integer paymentsAmount;
            try {
                paymentsAmount = service.getAmountWithUserId(userId);
            } catch (ServiceException e) {
                paymentsAmount = 0;
                logger.error(e.getErrorCode());
            }
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
