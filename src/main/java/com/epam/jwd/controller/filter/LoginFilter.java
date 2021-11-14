package com.epam.jwd.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Objects;

//@WebFilter("/*")
public class LoginFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(LoginFilter.class);

    private static final String SIGNIN_PAGE_URL = "/payments?command=show_signin_page";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpSession session = ((HttpServletRequest) request).getSession();

        if (Objects.isNull(session.getAttribute("id"))
                && !Objects.isNull(request.getParameter("command"))
                && !request.getParameter("command").equals("show_signin")) {
            String url = ((HttpServletRequest) request).getContextPath() + SIGNIN_PAGE_URL;
            RequestDispatcher dispatcher = request.getRequestDispatcher(url);
            dispatcher.forward(request, response);
            logger.info("required data is not exists in session");
        } else {
            logger.info("is signed in filter passed");
            chain.doFilter(request, response);
        }
    }
}