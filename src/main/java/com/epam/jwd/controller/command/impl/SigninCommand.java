package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
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

    private static final String USER_MAIN_PAGE_URL = "/jsp/usermain.jsp";
    private static final String ERROR_PAGE_URL = "/jsp/errorpage.jsp";

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {
        UserService service = new UserService();
        try {
            UserDto userDto = service.getByLogin(request.getParameter("login"));
            if (Objects.equals(userDto.getPassword(), request.getParameter("password"))){
                logger.info("password is correct");
                return actionDataCorrect(request, userDto);
            } else {
                logger.info("password is wrong");
            }
        } catch (ServiceException e) {
            if (e.getErrorCode() == ExceptionCode.USER_IS_NOT_FOUND_EXCEPTION_CODE){
                logger.info("login is wrong");
            } else {
                logger.info("something else is wrong");
            }
        }
        return new CommandResponse(request.getContextPath() + ERROR_PAGE_URL, true);
    }

    private CommandResponse actionDataCorrect(HttpServletRequest request, UserDto userDto){

        HttpSession session = request.getSession();
        session.setAttribute("role", userDto.getRole());
        session.setAttribute("id", userDto.getId());

        return new CommandResponse(request.getContextPath() + USER_MAIN_PAGE_URL, true);
    }
}
