package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import com.epam.jwd.controller.command.impl.showpage.ShowEditPaymentCommand;
import com.epam.jwd.service.dto.userdto.AccountDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.AccountService;
import com.epam.jwd.service.impl.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommitAccountCreationCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ShowEditPaymentCommand.class);

    private static final String SHOW_ACCOUNT_PAGE_URL = "/payments?command=show_account";

    AccountService accountService = new AccountService();
    UserService userService = new UserService();

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info(CommitAccountChangesCommand.class);

        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("id");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        Integer pictureId;
        try{
            pictureId = Integer.valueOf(request.getParameter("pictureId"));
        } catch ( NumberFormatException e){
            logger.error(e);
            pictureId = -1;
        }

        try {
            UserDto user = userService.getById(userId);
            AccountDto account = new AccountDto(name, surname,
                    pictureId != -1
                            ? pictureId
                            : 1);
            account = accountService.create(account);
            user.setAccountId(account.getId());
            userService.update(user);
        } catch (ServiceException e) {
            logger.error(e.getErrorCode());
        }

        return new CommandResponse(request.getContextPath() + SHOW_ACCOUNT_PAGE_URL, true);
    }

}
