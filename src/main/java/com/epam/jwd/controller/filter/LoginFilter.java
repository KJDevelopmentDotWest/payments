package com.epam.jwd.controller.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Objects;

@WebFilter("/*")
public class LoginFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(LoginFilter.class);

    private static final String SIGNIN_PAGE_URL = "/jsp/signin.jsp";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpSession session = ((HttpServletRequest) request).getSession();

        if (Objects.isNull(session.getAttribute("id"))
                && !Objects.isNull(request.getParameter("command"))
                && !request.getParameter("command").equals("signin")) {
            String url = ((HttpServletRequest) request).getContextPath() + SIGNIN_PAGE_URL;
            ((HttpServletResponse) response).sendRedirect(url);
            logger.info("required data is not exists in session");
        } else {
            logger.info("is login filter passed");
            chain.doFilter(request, response);
        }
    }
}