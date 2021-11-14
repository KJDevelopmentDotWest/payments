package com.epam.jwd.controller.command.impl;

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

public class AddCreditCardCommand implements Command {

    private static final Logger logger = LogManager.getLogger(AddCreditCardCommand.class);

    private static final String USER_CREDIT_CARDS_PAGE_URL = "/payments?command=show_credit_cards&currentPage=1";

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();

        Date date = new Date();
        date.setYear(date.getYear()+3);

        CreditCardDto creditCard = new CreditCardDto(
                new BankAccountDto(
                        0L,
                        false
                ),
                request.getParameter("name"),
                date,
                (Integer) session.getAttribute("id"),
                Long.valueOf(request.getParameter("cardNumber"))
        );

        CreditCardService service = new CreditCardService();
        try {
            service.create(creditCard);
        } catch (ServiceException e) {
            if (e.getErrorCode() == ExceptionCode.CREDIT_CARD_NUMBER_IS_NOT_UNIQUE_EXCEPTION_CODE){
                logger.info("caught");
                //todo implement error message
            }
            logger.error(e.getErrorCode());
        }

        return new CommandResponse(request.getContextPath() + USER_CREDIT_CARDS_PAGE_URL, true);
    }
}
