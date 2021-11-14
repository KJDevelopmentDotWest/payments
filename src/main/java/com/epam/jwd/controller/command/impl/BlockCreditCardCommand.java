package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import com.epam.jwd.controller.command.impl.showpage.ShowEditPaymentCommand;
import com.epam.jwd.service.dto.creditcarddto.CreditCardDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.CreditCardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BlockCreditCardCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ShowEditPaymentCommand.class);

    private static final String USER_PAYMENTS_PAGE_URL = "/payments?command=credit_cards&currentPage=1";

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info(BlockCreditCardCommand.class);

        Integer creditCardId = Integer.valueOf( request.getParameter("creditCardId"));
        CreditCardService service = new CreditCardService();
        try {
            CreditCardDto creditCard = service.getById(creditCardId);
            creditCard.getBankAccount().setBlocked(true);
            service.update(creditCard);
        } catch (ServiceException e) {
            logger.error(e.getErrorCode());
        }

        return new CommandResponse(request.getContextPath() + USER_PAYMENTS_PAGE_URL, true);
    }
}
