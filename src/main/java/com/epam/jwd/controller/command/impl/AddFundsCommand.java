package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import com.epam.jwd.service.dto.creditcarddto.CreditCardDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.CreditCardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddFundsCommand implements Command {

    private static final Logger logger = LogManager.getLogger(AddFundsCommand.class);

    private static final String SHOW_CREDIT_CARDS_PAGE_URL = "/payments?command=show_credit_cards&currentPage=1";

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {

        logger.info(AddFundsCommand.class);

        Integer creditCardId = Integer.valueOf( request.getParameter("creditCardId"));
        Integer funds = Integer.valueOf(request.getParameter("funds"));
        CreditCardService service = new CreditCardService();

        try {
            CreditCardDto creditCard = service.getById(creditCardId);
            creditCard.getBankAccount().setBalance(creditCard.getBankAccount().getBalance() + funds);
            service.update(creditCard);
        } catch (ServiceException e) {
            logger.error(e.getErrorCode());
        }

        return new CommandResponse(request.getContextPath() + SHOW_CREDIT_CARDS_PAGE_URL, true);
    }
}
