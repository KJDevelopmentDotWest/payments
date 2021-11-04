package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import com.epam.jwd.service.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class UserMainCommand implements Command {
    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            PrintWriter printWriter = response.getWriter();
            printWriter.print(request.getRequestURI());
            printWriter.print("adasd");
        } catch (IOException e) {
            //todo
        }
        return new CommandResponse( request.getContextPath() + "/usermain.jsp", false);
    }
}
