package com.epam.jwd.controller.command.impl.action;

import com.epam.jwd.controller.command.ApplicationCommand;
import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import com.epam.jwd.service.dto.creditcarddto.CreditCardDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.CreditCardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class UnblockCreditCardCommand implements Command {
    private static final Logger logger = LogManager.getLogger(BlockCreditCardCommand.class);

    private static final String SHOW_ADMIN_CREDIT_CARDS_PAGE_URL = "/payments?command=show_admin_credit_cards&currentPage=1";

    private static final String CREDIT_CARD_ID_PARAMETER_NAME = "creditCardId";

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("command " + UnblockCreditCardCommand.class);

        Integer creditCardId;
        try {
            creditCardId = Integer.valueOf(request.getParameter(CREDIT_CARD_ID_PARAMETER_NAME));
        } catch (NumberFormatException e){
            logger.error(e);
            return new CommandResponse(request.getContextPath() + ApplicationCommand.SHOW_ERROR_PAGE_URL, true);
        }

        CreditCardService service = new CreditCardService();
        try {
            CreditCardDto creditCard = service.getById(creditCardId);
            creditCard.getBankAccount().setBlocked(false);
            service.update(creditCard);
        } catch (ServiceException e) {
            logger.error(e.getErrorCode());
        }

        return new CommandResponse(request.getContextPath() + SHOW_ADMIN_CREDIT_CARDS_PAGE_URL, true);
    }
}
