package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.ApplicationCommand;
import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import com.epam.jwd.service.dto.creditcarddto.BankAccountDto;
import com.epam.jwd.service.dto.creditcarddto.CreditCardDto;
import com.epam.jwd.service.exception.ExceptionCode;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.CreditCardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

public class CommitCreditCardAddingCommand implements Command {

    private static final Logger logger = LogManager.getLogger(CommitCreditCardAddingCommand.class);

    private static final String USER_CREDIT_CARDS_PAGE_URL = "/payments?command=show_credit_cards&currentPage=1";
    private static final String ADD_CREDIT_CARD_PAGE_URL = "/payments?command=show_add_credit_card";

    private static final String NAME_PARAMETER_NAME = "name";
    private static final String CURD_NUMBER_PARAMETER_NAME = "cardNumber";
    private static final String INCORRECT_ATTRIBUTE_NAME = "incorrect";

    private static final String CREDIT_CARD_ALREADY_EXISTS = "Credit card with provided number already exists";

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {

        logger.info("command " + CommitCreditCardAddingCommand.class);

        HttpSession session = request.getSession();

        Date date = new Date();
        date.setYear(date.getYear()+3);

        CreditCardDto creditCard = new CreditCardDto(
                new BankAccountDto(
                        0L,
                        false
                ),
                request.getParameter(NAME_PARAMETER_NAME),
                date,
                (Integer) session.getAttribute(ID_ATTRIBUTE_NAME),
                Long.valueOf(request.getParameter(CURD_NUMBER_PARAMETER_NAME))
        );

        CreditCardService service = new CreditCardService();
        try {
            service.create(creditCard);
            request.getSession().removeAttribute(INCORRECT_ATTRIBUTE_NAME);
            return new CommandResponse(request.getContextPath() + USER_CREDIT_CARDS_PAGE_URL, true);
        } catch (ServiceException e) {
            logger.error(e.getErrorCode());
            if (e.getErrorCode() == ExceptionCode.CREDIT_CARD_NUMBER_IS_NOT_UNIQUE_EXCEPTION_CODE){
                request.getSession().setAttribute(INCORRECT_ATTRIBUTE_NAME, CREDIT_CARD_ALREADY_EXISTS);
                return new CommandResponse(request.getContextPath() + ADD_CREDIT_CARD_PAGE_URL, true);
            } else {
                return new CommandResponse(request.getContextPath() + ApplicationCommand.SHOW_ERROR_PAGE_URL, true);
            }
        }
    }
}
