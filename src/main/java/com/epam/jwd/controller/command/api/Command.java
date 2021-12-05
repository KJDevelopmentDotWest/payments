package com.epam.jwd.controller.command.api;

import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface Command {

    String ID_ATTRIBUTE_NAME = "id";

    String CURRENT_PAGE_PARAMETER_NAME = "currentPage";
    String LAST_PAGE_PARAMETER_NAME = "lastPage";
    String SORT_BY_PARAMETER_NAME = "sortBy";

    CommandResponse execute(HttpServletRequest request, HttpServletResponse response);

}
