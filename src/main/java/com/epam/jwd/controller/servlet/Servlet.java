
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

    private static final String COMMAND_PARAMETER_NAME = "command";

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CommandResponse commandResponse = ApplicationCommand.getByString(request.getParameter(COMMAND_PARAMETER_NAME))
                .getCommand()
                .execute(request, response);

        if (commandResponse.getRedirect()) {
            response.sendRedirect(commandResponse.getURL());
        } else {
            RequestDispatcher dispatcher = getServletContext()
                    .getRequestDispatcher(commandResponse.getURL());
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
}
