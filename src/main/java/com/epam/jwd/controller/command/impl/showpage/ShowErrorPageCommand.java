package com.epam.jwd.controller.command.impl.showpage;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShowErrorPageCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ShowErrorPageCommand.class);

    private static final String ERROR_PAGE_URL = "/WEB-INF/jsp/errorpage.jsp";

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("command " + ShowErrorPageCommand.class);
        return new CommandResponse(request.getContextPath() + ERROR_PAGE_URL, false);
    }
}
