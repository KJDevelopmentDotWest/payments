package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import com.epam.jwd.dao.model.user.Role;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommitUserCreationCommand implements Command {

    private static final Logger logger = LogManager.getLogger(CommitUserCreationCommand.class);

    private static final String SHOW_SIGNIN_PAGE_URL = "/payments?command=show_signin";

    UserService service = new UserService();

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {

        logger.info("command " + CommitUserCreationCommand.class);

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        UserDto userDto = new UserDto(login, password, null, true, Role.CUSTOMER);

        try {
            service.create(userDto);
        } catch (ServiceException e) {
            logger.error(e.getErrorCode());
        }

        return new CommandResponse(request.getContextPath() + SHOW_SIGNIN_PAGE_URL, true);
    }
}
