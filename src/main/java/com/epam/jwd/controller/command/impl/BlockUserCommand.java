package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
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

public class BlockUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger(BlockUserCommand.class);

    private static final String SIGNIN_PAGE_URL = "/payments?command=show_signin";

    UserService userService = new UserService();

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("command " + BlockUserCommand.class);
        HttpSession session = request.getSession();
        Integer id = (Integer) session.getAttribute(ID_ATTRIBUTE_NAME);
        try {
            UserDto userDto = userService.getById(id);
            userDto.setActive(false);
            userService.update(userDto);
        } catch (ServiceException e) {
            logger.error(e.getErrorCode());
        }
        return new CommandResponse( request.getContextPath() + SIGNIN_PAGE_URL, true);
    }
}
