package com.epam.jwd.controller.command.impl.showpage;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ShowCreateUserCommand implements Command {

    private static final String CREATE_USER_PAGE_URL = "/WEB-INF/jsp/createuser.jsp";

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {
        return new CommandResponse(request.getContextPath() + CREATE_USER_PAGE_URL, false);
    }
}
