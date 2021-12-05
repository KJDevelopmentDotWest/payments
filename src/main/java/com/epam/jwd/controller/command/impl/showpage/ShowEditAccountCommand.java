package com.epam.jwd.controller.command.impl.showpage;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import com.epam.jwd.service.dto.userdto.AccountDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.AccountService;
import com.epam.jwd.service.impl.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShowEditAccountCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ShowEditAccountCommand.class);

    private static final String EDIT_ACCOUNT_PAGE_URL = "/WEB-INF/jsp/editaccount.jsp";

    private static final String ACCOUNT_ATTRIBUTE_NAME = "account";

    private static final AccountService accountService = new AccountService();
    private static final UserService userService = new UserService();

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("command " + ShowEditAccountCommand.class);

        HttpSession session = request.getSession();
        Integer id = (Integer) session.getAttribute(ID_ATTRIBUTE_NAME);

        try {
            AccountDto account = accountService.getById(
                    userService.getById(id)
                            .getAccountId()
            );
            request.setAttribute(ACCOUNT_ATTRIBUTE_NAME, account);
        } catch (ServiceException e) {
            logger.error(e);
        }

        return new CommandResponse(request.getContextPath() + EDIT_ACCOUNT_PAGE_URL, false);
    }
}
