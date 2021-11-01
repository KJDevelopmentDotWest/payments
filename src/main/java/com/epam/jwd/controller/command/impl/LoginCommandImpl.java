package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class LoginCommandImpl implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        Service<UserDto, Integer> service = new UserService();
        try {
            PrintWriter printWriter = response.getWriter();
            printWriter.print(service.getAll().toString());
        } catch (ServiceException | IOException e) {
            //todo
        }
    }
}
