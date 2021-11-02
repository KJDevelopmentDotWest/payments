package com.epam.jwd.controller.servlet;

import com.epam.jwd.controller.command.ApplicationCommand;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/payments")
public class Servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        CommandResponse commandResponse = ApplicationCommand.getByString(req.getParameter("command"))
                .getCommand()
                .execute(req, resp);

        resp.setStatus(200);

        if (commandResponse.getRedirect()) {
            resp.sendRedirect(commandResponse.getURL());
        } else {
            RequestDispatcher dispatcher = getServletContext()
                    .getRequestDispatcher(commandResponse.getURL());
            dispatcher.forward(req, resp);
        }
    }
}
