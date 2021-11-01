package com.epam.jwd.controller.servlet;

import com.epam.jwd.controller.command.CommandList;
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

        CommandList.getByString(req.getParameter("command"))
                .getCommand()
                .execute(req, resp);

        resp.setStatus(200);
    }
}
