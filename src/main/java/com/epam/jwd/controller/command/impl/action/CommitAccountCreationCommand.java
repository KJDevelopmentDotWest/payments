package com.epam.jwd.controller.command.impl.action;

import com.epam.jwd.controller.command.ApplicationCommand;
import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
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

import java.util.Objects;

public class CommitAccountCreationCommand implements Command {

    private static final Logger logger = LogManager.getLogger(CommitAccountCreationCommand.class);

    private static final String SHOW_ACCOUNT_PAGE_URL = "/payments?command=show_account";

    private static final String NAME_PARAMETER_NAME = "name";
    private static final String SURNAME_PARAMETER_NAME = "surname";
    private static final String PICTURE_ID_PARAMETER_NAME = "pictureId";

    AccountService accountService = new AccountService();
    UserService userService = new UserService();

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("command " + CommitAccountCreationCommand.class);

        HttpSession session = request.getSession();

        Integer userId = (Integer) session.getAttribute(ID_ATTRIBUTE_NAME);
        String name;
        String surname;
        Integer pictureId;

        if (Objects.isNull(userId)){
            return new CommandResponse(request.getContextPath() + ApplicationCommand.SHOW_ERROR_PAGE_URL, true);
        }

        try {
            name = request.getParameter(NAME_PARAMETER_NAME);
            surname = request.getParameter(SURNAME_PARAMETER_NAME);
        } catch (NumberFormatException e){
            logger.error(e);
            return new CommandResponse(request.getContextPath() + ApplicationCommand.SHOW_ERROR_PAGE_URL, true);
        }

        try{
            pictureId = Integer.valueOf(request.getParameter(PICTURE_ID_PARAMETER_NAME));
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
