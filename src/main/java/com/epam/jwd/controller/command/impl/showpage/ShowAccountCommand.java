package com.epam.jwd.controller.command.impl.showpage;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShowAccountCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ShowAccountCommand.class);

    private static final String USER_MAIN_PAGE_URL = "/WEB-INF/jsp/usermain.jsp";

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("command " + ShowAccountCommand.class);
        return new CommandResponse(request.getContextPath() + USER_MAIN_PAGE_URL, false);
    }
}
