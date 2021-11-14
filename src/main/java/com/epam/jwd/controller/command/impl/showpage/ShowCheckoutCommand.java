package com.epam.jwd.controller.command.impl.showpage;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShowCheckoutCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ShowCheckoutCommand.class);

    private static final String CHECKOUT_PAGE_URL = "/WEB-INF/jsp/paymentcheckout.jsp";

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info(ShowCheckoutCommand.class);
        return new CommandResponse(request.getContextPath() + CHECKOUT_PAGE_URL, false);
    }
}
