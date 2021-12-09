package com.epam.jwd.controller.command.impl.action;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;

public class SignoutCommand implements Command {

    private static final Logger logger = LogManager.getLogger(SignoutCommand.class);

    private static final String SIGNIN_PAGE_URL = "/payments?command=show_signin";

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {

        logger.info("command " + SignoutCommand.class);
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        HttpSession session = request.getSession();
        Collections.list(session.getAttributeNames())
                .forEach(session::removeAttribute);
        return new CommandResponse( request.getContextPath() + SIGNIN_PAGE_URL, true);
    }
}
