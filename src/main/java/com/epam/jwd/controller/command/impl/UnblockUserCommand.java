package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UnblockUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger(UnblockUserCommand.class);

    private static final String SHOW_ADMIN_USERS_PAGE_URL = "/payments?command=show_admin_users&currentPage=1";

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("command " + UnblockUserCommand.class);

        Integer creditCardId = Integer.valueOf( request.getParameter("userId"));
        UserService service = new UserService();
        try {
            UserDto userDto = service.getById(creditCardId);
            userDto.setActive(true);
            service.update(userDto);
        } catch (ServiceException e) {
            logger.error(e.getErrorCode());
        }

        return new CommandResponse(request.getContextPath() + SHOW_ADMIN_USERS_PAGE_URL, true);
    }
}
