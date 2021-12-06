package com.epam.jwd.controller.command.impl.action;

import com.epam.jwd.controller.command.ApplicationCommand;
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

    private static final String USER_ID_PARAMETER_NAME = "userId";

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("command " + UnblockUserCommand.class);

        Integer userId;

        try {
            userId = Integer.valueOf( request.getParameter(USER_ID_PARAMETER_NAME));
        } catch (NumberFormatException e){
            logger.error(e);
            return new CommandResponse(request.getContextPath() + ApplicationCommand.SHOW_ERROR_PAGE_URL, true);
        }

        UserService service = new UserService();
        try {
            UserDto userDto = service.getById(userId);
            userDto.setActive(true);
            service.update(userDto);
        } catch (ServiceException e) {
            logger.error(e.getErrorCode());
        }

        return new CommandResponse(request.getContextPath() + SHOW_ADMIN_USERS_PAGE_URL, true);
    }
}
