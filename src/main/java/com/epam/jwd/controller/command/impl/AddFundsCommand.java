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

import java.math.BigInteger;

public class AddFundsCommand implements Command {

    private static final Logger logger = LogManager.getLogger(AddFundsCommand.class);

    private static final String CREDIT_CARD_ID_PARAMETER_NAME = "creditCardId";
    private static final String FUNDS_PARAMETER_NAME = "funds";

    private static final String SHOW_CREDIT_CARDS_PAGE_URL = "/payments?command=show_credit_cards&currentPage=1";

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {

        logger.info("command " + AddFundsCommand.class);

        Integer creditCardId = Integer.valueOf( request.getParameter(CREDIT_CARD_ID_PARAMETER_NAME));
        Integer funds = Integer.valueOf(request.getParameter(FUNDS_PARAMETER_NAME));
        CreditCardService service = new CreditCardService();

        try {
            CreditCardDto creditCard = service.getById(creditCardId);
            Long currentBalance = creditCard.getBankAccount().getBalance();
            BigInteger currentBalanceBig = new BigInteger(String.valueOf(currentBalance));
            BigInteger fundsToAddBig = new BigInteger(String.valueOf(funds));
            BigInteger sum = currentBalanceBig.add(fundsToAddBig);
            if (sum.compareTo(new BigInteger(String.valueOf(Long.MAX_VALUE))) > 0){
                creditCard.getBankAccount().setBalance(currentBalance + funds);
            } else {
                creditCard.getBankAccount().setBalance(Long.MAX_VALUE);
            }
            service.update(creditCard);
        } catch (ServiceException e) {
            logger.error(e.getErrorCode());
        }

        return new CommandResponse(request.getContextPath() + SHOW_CREDIT_CARDS_PAGE_URL, true);
    }
}
