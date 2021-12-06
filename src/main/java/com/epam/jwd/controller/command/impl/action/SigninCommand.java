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

import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class SigninCommand implements Command {

    private static final Logger logger = LogManager.getLogger(SigninCommand.class);

    private static final String SHOW_SIGNIN_PAGE_URL = "/payments?command=show_signin";
    private static final String SHOW_USER_MAIN_PAGE_URL = "/payments?command=show_account";
    private static final String SHOW_ADMIN_MAIN_PAGE_URL = "/payments?command=show_admin_users";

    private static final String LOGIN_PARAMETER_NAME = "login";
    private static final String PASSWORD_PARAMETER_NAME = "password";
    private static final String INCORRECT_ATTRIBUTE_NAME = "incorrect";

    private static final String LOGIN_WRONG_MESSAGE = "Login is wrong";
    private static final String PASSWORD_WRONG_MESSAGE = "Password is wrong";
    private static final String USER_BLOCKED_MESSAGE = "User is blocked";


    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {

        logger.info("command " + SigninCommand.class);
        UserService service = new UserService();

        String login = request.getParameter(LOGIN_PARAMETER_NAME);
        String password = request.getParameter(PASSWORD_PARAMETER_NAME);

        if (Objects.isNull(login) || Objects.isNull(password)){
            logger.error("required data not exists");
            return new CommandResponse(request.getContextPath() + ApplicationCommand.SHOW_ERROR_PAGE_URL, true);
        }

        try {
            UserDto userDto = service.getByLogin(request.getParameter(LOGIN_PARAMETER_NAME));
            if (Objects.equals(userDto.getPassword(), request.getParameter(PASSWORD_PARAMETER_NAME))){
                logger.info("login and password are correct");
                return actionDataCorrect(request, userDto);
            } else {
                logger.info("password is wrong");
                return actionDataIncorrect(request, PASSWORD_WRONG_MESSAGE);
            }
        } catch (ServiceException e) {
            logger.error(e.getErrorCode());
            if (e.getErrorCode() == ExceptionCode.USER_IS_NOT_FOUND_EXCEPTION_CODE){
                logger.info("login is wrong");
                return actionDataIncorrect(request, LOGIN_WRONG_MESSAGE);
            } else {
                logger.info("something wrong");
                return new CommandResponse(request.getContextPath() + ApplicationCommand.SHOW_ERROR_PAGE_URL, true);
            }

        }
    }

    private CommandResponse actionDataIncorrect(HttpServletRequest request, String message){
        logger.info("data incorrect");
        request.getSession().setAttribute(INCORRECT_ATTRIBUTE_NAME, message);
        return new CommandResponse(request.getContextPath() + SHOW_SIGNIN_PAGE_URL, true);
    }

    private CommandResponse actionDataCorrect(HttpServletRequest request, UserDto userDto){
        HttpSession session = request.getSession();
        session.removeAttribute(INCORRECT_ATTRIBUTE_NAME);
        session.setAttribute("role", userDto.getRole());
        session.setAttribute("id", userDto.getId());

        if (userDto.getRole().equals(Role.CUSTOMER)){
            if (userDto.getActive()) {
                return new CommandResponse(request.getContextPath() + SHOW_USER_MAIN_PAGE_URL, true);
            } else {
                return actionDataIncorrect(request, USER_BLOCKED_MESSAGE);
            }
        } else {
            return new CommandResponse(request.getContextPath() + SHOW_ADMIN_MAIN_PAGE_URL, true);
        }
    }
}
