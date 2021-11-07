package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PaymentsPageCommandImpl implements Command {

    private static final String USER_PAYMENTS_PAGE_URL = "/jsp/payments.jsp";

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {
        return new CommandResponse(request.getContextPath() + USER_PAYMENTS_PAGE_URL, true);
    }
}
