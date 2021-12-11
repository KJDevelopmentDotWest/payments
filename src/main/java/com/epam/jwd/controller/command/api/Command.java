package com.epam.jwd.controller.command.api;

import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface Command {

    String ID_ATTRIBUTE_NAME = "id";

    String CURRENT_PAGE_PARAMETER_NAME = "currentPage";
    String LAST_PAGE_PARAMETER_NAME = "lastPage";
    String SORT_BY_PARAMETER_NAME = "sortBy";

    /**
     *
     * @param request HTTP servlet request that method will operate
     * @param response HTTP servlet response that method will operate
     * @return Command response
     */
    CommandResponse execute(HttpServletRequest request, HttpServletResponse response);

}
