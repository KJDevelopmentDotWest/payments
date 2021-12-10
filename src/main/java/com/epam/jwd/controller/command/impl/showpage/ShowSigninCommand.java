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

    private static final String INCORRECT_ATTRIBUTE_NAME = "incorrect";
    private static final String SUCCESS_ATTRIBUTE_NAME = "success";

    private static final String HEADER_NAME = "Cache-Control";
    private static final String HEADER_VALUE = "no-cache, no-store, must-revalidate";

    private static final String LOGIN_PAGE_URL = "/WEB-INF/jsp/signin.jsp";

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("command " + ShowSigninCommand.class);
        response.setHeader(HEADER_NAME, HEADER_VALUE);
        HttpSession session = request.getSession();
        request.setAttribute(INCORRECT_ATTRIBUTE_NAME, session.getAttribute(INCORRECT_ATTRIBUTE_NAME));
        session.removeAttribute(INCORRECT_ATTRIBUTE_NAME);
        request.setAttribute(SUCCESS_ATTRIBUTE_NAME, session.getAttribute(SUCCESS_ATTRIBUTE_NAME));
        session.removeAttribute(SUCCESS_ATTRIBUTE_NAME);
        return new CommandResponse(request.getContextPath() + LOGIN_PAGE_URL, false);
    }
}
