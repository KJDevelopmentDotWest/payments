package com.epam.jwd.controller.command.impl.showpage;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShowAddCreditCardCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ShowAddCreditCardCommand.class);

    private static final String INCORRECT_ATTRIBUTE_NAME = "incorrect";

    private static final String ADD_CREDIT_CARD_PAGE_URL = "/WEB-INF/jsp/addcreditcard.jsp";

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("command " + ShowAddCreditCardCommand.class);
        HttpSession session = request.getSession();
        request.setAttribute(INCORRECT_ATTRIBUTE_NAME, session.getAttribute(INCORRECT_ATTRIBUTE_NAME));
        session.removeAttribute(INCORRECT_ATTRIBUTE_NAME);
        return new CommandResponse(request.getContextPath() + ADD_CREDIT_CARD_PAGE_URL, false);
    }
}
