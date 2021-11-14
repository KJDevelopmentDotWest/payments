package com.epam.jwd.controller.command.impl.showpage;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ShowAddCreditCardCommand implements Command {

    private static final String ADD_CREDIT_CARD_PAGE_URL = "/WEB-INF/jsp/addcreditcard.jsp";

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {
        return new CommandResponse(request.getContextPath() + ADD_CREDIT_CARD_PAGE_URL, false);
    }
}
