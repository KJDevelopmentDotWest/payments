package com.epam.jwd.controller.filter;


import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebFilter("/*")
public class LoginFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(LoginFilter.class);

    private static final String SIGNIN_PAGE_URL = "/payments?command=show_signin_page";

    private static final List<String> allowedWithoutLogin = List.of("show_signin", "signin", "show_create_user", "commit_user_creation", "signout");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest){
            HttpSession session = ((HttpServletRequest) request).getSession();

            if (Objects.isNull(session.getAttribute("id"))){
                if (!Objects.isNull(request.getParameter("command"))){
                    if (allowedWithoutLogin.contains(request.getParameter("command"))){
                        forwardToNextFilter(request, response, chain);
                    } else {
                        forwardToLoginPage(request, response);
                    }
                } else {
                    forwardToLoginPage(request, response);
                }
            } else {
                forwardToNextFilter(request, response, chain);
            }
        }
    }

    private void forwardToNextFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        logger.info("is signed in filter passed");
        chain.doFilter(request, response);
    }

    protected void forwardToLoginPage(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        String url = ((HttpServletRequest) request).getContextPath() + SIGNIN_PAGE_URL;
        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
        logger.info("required data is not exists in session");
    }
}