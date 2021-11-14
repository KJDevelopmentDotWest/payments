package com.epam.jwd.controller.command.impl.showpage;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ShowSigninPage implements Command {

    private static final String LOGIN_PAGE_URL = "/WEB-INF/jsp/signin.jsp";

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {
        return new CommandResponse(request.getContextPath() + LOGIN_PAGE_URL, false);
    }
}
