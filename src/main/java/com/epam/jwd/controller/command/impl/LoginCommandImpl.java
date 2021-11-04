package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.exception.ExceptionCode;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

public class LoginCommandImpl implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {
        Service<UserDto, Integer> service = new UserService();
        try {
            UserDto userDto = ((UserService)service).getByLogin(request.getParameter("login"));
            if (Objects.equals(userDto.getPassword(), request.getParameter("password"))){
                logger.info("password is correct");
                return new CommandResponse(request.getContextPath() + "/jsp/usermain.jsp", true);
            } else {
                logger.info("password is wrong");
            }
        } catch (ServiceException e) {
            if (e.getErrorCode() == ExceptionCode.USER_IS_NOT_FOUND_EXCEPTION_CODE){
                logger.info("login is wrong");
                try {
                    PrintWriter printWriter = response.getWriter();
                    printWriter.print("login is wrong");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                logger.info("something else is wrong");
            }
        }
        return new CommandResponse(request.getContextPath() + "/jsp/errorpage.jsp", true);
    }
}
