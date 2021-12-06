package com.epam.jwd.controller.command.impl.action;

import com.epam.jwd.controller.command.ApplicationCommand;
import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import com.epam.jwd.dao.model.user.Role;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.exception.ExceptionCode;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class CommitUserCreationCommand implements Command {

    private static final Logger logger = LogManager.getLogger(CommitUserCreationCommand.class);

    private static final String SHOW_SIGNIN_PAGE_URL = "/payments?command=show_signin";
    private static final String SHOW_CREATE_USER_PAGE_URL = "/payments?command=show_create_user";

    private static final String INCORRECT_ATTRIBUTE_NAME = "incorrect";
    private static final String LOGIN_PARAMETER_NAME = "login";
    private static final String PASSWORD_PARAMETER_NAME = "password";

    private static final String LOGIN_NOT_UNIQUE_MESSAGE = "User with provided login already exists";

    UserService service = new UserService();

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {

        logger.info("command " + CommitUserCreationCommand.class);

        String login = request.getParameter(LOGIN_PARAMETER_NAME);
        String password = request.getParameter(PASSWORD_PARAMETER_NAME);

        if (Objects.isNull(login) || Objects.isNull(password)){
            logger.error("required data not exists");
            return new CommandResponse(request.getContextPath() + ApplicationCommand.SHOW_ERROR_PAGE_URL, true);
        }

        UserDto userDto = new UserDto(login, password, null, true, Role.CUSTOMER);

        try {
            service.create(userDto);
            request.getSession().removeAttribute(INCORRECT_ATTRIBUTE_NAME);
            return new CommandResponse(request.getContextPath() + SHOW_SIGNIN_PAGE_URL, true);
        } catch (ServiceException e) {
            logger.error(e.getErrorCode());
            if (e.getErrorCode() == ExceptionCode.USER_LOGIN_IS_NOT_UNIQUE_EXCEPTION_CODE){
                request.getSession().setAttribute(INCORRECT_ATTRIBUTE_NAME, LOGIN_NOT_UNIQUE_MESSAGE);
                return new CommandResponse(request.getContextPath() + SHOW_CREATE_USER_PAGE_URL, true);
            }
        }

        return new CommandResponse(request.getContextPath() + SHOW_SIGNIN_PAGE_URL, true);
    }
}
