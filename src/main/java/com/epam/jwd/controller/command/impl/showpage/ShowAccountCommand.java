package com.epam.jwd.controller.command.impl.showpage;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ShowAccountCommand implements Command {

    private static final String USER_MAIN_PAGE_URL = "/WEB-INF/jsp/usermain.jsp";

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {
        return new CommandResponse(request.getContextPath() + USER_MAIN_PAGE_URL, false);
    }
}
