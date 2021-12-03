package com.epam.jwd.controller.command.impl.showpage;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShowSigninCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ShowSigninCommand.class);

    private static final String LOGIN_PAGE_URL = "/WEB-INF/jsp/signin.jsp";

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("command " + ShowSigninCommand.class);
        HttpSession session = request.getSession();
        request.setAttribute("incorrect", session.getAttribute("incorrect"));
        session.removeAttribute("incorrect");
        return new CommandResponse(request.getContextPath() + LOGIN_PAGE_URL, false);
    }
}
