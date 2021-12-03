package com.epam.jwd.controller.command.impl;

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


    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {

        logger.info("command " + SigninCommand.class);
        UserService service = new UserService();
        try {
            UserDto userDto = service.getByLogin(request.getParameter("login"));
            if (Objects.equals(userDto.getPassword(), request.getParameter("password"))){
                logger.info("login and password are correct");
                return actionDataCorrect(request, userDto);
            } else {
                logger.info("password is wrong");
                return actionDataIncorrect(request, "Password is wrong");
            }
        } catch (ServiceException e) {
            logger.error(e.getErrorCode());
            if (e.getErrorCode() == ExceptionCode.USER_IS_NOT_FOUND_EXCEPTION_CODE){
                logger.info("login is wrong");
                return actionDataIncorrect(request, "Login is wrong");
            } else {
                logger.info("something wrong");
                return new CommandResponse(request.getContextPath() + ApplicationCommand.SHOW_ERROR_PAGE_URL, true);
            }

        }
    }

    private CommandResponse actionDataIncorrect(HttpServletRequest request, String message){
        logger.info("data incorrect");
        request.getSession().setAttribute("incorrect", message);
        return new CommandResponse(request.getContextPath() + SHOW_SIGNIN_PAGE_URL, true);
    }

    private CommandResponse actionDataCorrect(HttpServletRequest request, UserDto userDto){
        HttpSession session = request.getSession();
        session.removeAttribute("incorrect");
        session.setAttribute("role", userDto.getRole());
        session.setAttribute("id", userDto.getId());

        if (userDto.getRole().equals(Role.CUSTOMER)){
            if (userDto.getActive()) {
                return new CommandResponse(request.getContextPath() + SHOW_USER_MAIN_PAGE_URL, true);
            } else {
                return actionDataIncorrect(request, "User is blocked");
            }
        } else {
            return new CommandResponse(request.getContextPath() + SHOW_ADMIN_MAIN_PAGE_URL, true);
        }
    }
}
