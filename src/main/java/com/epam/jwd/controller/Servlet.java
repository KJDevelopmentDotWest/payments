package com.epam.jwd.controller;

import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.creditcarddto.CreditCardDto;
import com.epam.jwd.service.dto.paymentdto.PaymentDto;
import com.epam.jwd.service.dto.userdto.RoleDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.CreditCardService;
import com.epam.jwd.service.impl.PaymentService;
import com.epam.jwd.service.impl.RoleService;
import com.epam.jwd.service.impl.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/payments")
public class Servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter printWriter = resp.getWriter();
        Service<UserDto, Integer> userService = new UserService();
        Service<RoleDto, Integer> roleService = new RoleService();
        Service<CreditCardDto, Integer> creditCardService = new CreditCardService();
        Service<PaymentDto, Integer> paymentService = new PaymentService();
        try {
            List<UserDto> resultUsers = userService.getAll();
            List<RoleDto> resultRoles = roleService.getAll();
            List<CreditCardDto> resultCreditCards = creditCardService.getAll();
            List<PaymentDto> resultPayments = paymentService.getAll();
            printWriter.print("works\n");
            resultUsers.forEach(userDTO -> printWriter.print(userDTO.toString()));
            printWriter.print("works\n");
            resultRoles.forEach(roleDTO -> printWriter.print(roleDTO.toString()));
            printWriter.print("works\n");
            resultCreditCards.forEach(creditCardDTO -> printWriter.print(creditCardDTO.toString()));
            printWriter.print("works\n");
            resultPayments.forEach(paymentDTO -> printWriter.print(paymentDTO.toString()));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        resp.setStatus(200);
    }
}
