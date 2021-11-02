package com.epam.jwd.controller.command.api;

import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface Command {

    CommandResponse execute(HttpServletRequest request, HttpServletResponse response);

}
