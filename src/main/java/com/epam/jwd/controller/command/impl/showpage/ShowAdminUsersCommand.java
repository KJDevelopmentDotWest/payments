package com.epam.jwd.controller.command.impl.showpage;

import com.epam.jwd.controller.api.BiFunctionThrowsServiceException;
import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Objects;

public class ShowAdminUsersCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ShowAdminUsersCommand.class);

    private static final String ADMIN_PAYMENTS_PAGE_URL = "/WEB-INF/jsp/adminusers.jsp";
    private static final Integer MAX_ITEMS_IN_PAGE = 5;

    private static final String USERS_ATTRIBUTE_NAME = "users";
    private static final String STRING_NUM_1 = "1";

    private final UserService service = new UserService();

    private static final String KEY_ORDER_BY_ID = "id";
    private static final String KEY_ORDER_BY_LOGIN = "login";
    private static final String KEY_ORDER_BY_ROLE = "role";
    private static final String KEY_ORDER_BY_ACTIVE = "active";
    private static final String KEY_ORDER_BY_NAME = "name";
    private static final String KEY_ORDER_BY_SURNAME = "surname";
    private static final String KEY_ORDER_BY_PROFILE_PICTURE_ID = "profilePictureId";

    private final Map<String, BiFunctionThrowsServiceException<Integer, Integer, List<UserDto>>> orderByKeyToMethodMap;

    public ShowAdminUsersCommand(){
        orderByKeyToMethodMap = new HashMap<>();
        orderByKeyToMethodMap.put(KEY_ORDER_BY_ID, service::getSortedByIdWithinRange);
        orderByKeyToMethodMap.put(KEY_ORDER_BY_LOGIN, service::getSortedByLoginWithinRange);
        orderByKeyToMethodMap.put(KEY_ORDER_BY_ROLE, service::getSortedByRoleWithinRange);
        orderByKeyToMethodMap.put(KEY_ORDER_BY_ACTIVE, service::getSortedByActiveWithinRange);
        orderByKeyToMethodMap.put(KEY_ORDER_BY_NAME, service::getSortedByAccountNameWithinRange);
        orderByKeyToMethodMap.put(KEY_ORDER_BY_SURNAME, service::getSortedByAccountSurnameWithinRange);
        orderByKeyToMethodMap.put(KEY_ORDER_BY_PROFILE_PICTURE_ID, service::getSortedByAccountProfilePictureIdWithinRange);
    }

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("command " + ShowAdminUsersCommand.class);

        Integer pageNumber = Integer.valueOf(!Objects.isNull(request.getParameter(CURRENT_PAGE_PARAMETER_NAME)) ? request.getParameter(CURRENT_PAGE_PARAMETER_NAME) : STRING_NUM_1);
        Integer lastPage = getLastPage(request);
        List<UserDto> userDto;

        if (pageNumber > lastPage || pageNumber < 0){
            pageNumber = 1;
        }

        String sortBy = request.getParameter(SORT_BY_PARAMETER_NAME);

        try {
            userDto = orderByKeyToMethodMap.getOrDefault(sortBy, service::getSortedByIdWithinRange)
                    .apply(MAX_ITEMS_IN_PAGE, (pageNumber - 1) * MAX_ITEMS_IN_PAGE);
        } catch (ServiceException e) {
            logger.error(e.getErrorCode());
            userDto = new ArrayList<>();
        }


        request.setAttribute(USERS_ATTRIBUTE_NAME, userDto);
        request.setAttribute(CURRENT_PAGE_PARAMETER_NAME, pageNumber);
        request.setAttribute(SORT_BY_PARAMETER_NAME, sortBy);

        return new CommandResponse(request.getContextPath() + ADMIN_PAYMENTS_PAGE_URL, false);
    }

    private Integer getLastPage(HttpServletRequest request){
        Integer maxPage;
        if (Objects.isNull(request.getAttribute(LAST_PAGE_PARAMETER_NAME))){
            Integer creditCardsAmount;
            creditCardsAmount = service.getAmount();
            if (Double.compare(creditCardsAmount / MAX_ITEMS_IN_PAGE.doubleValue(), creditCardsAmount / MAX_ITEMS_IN_PAGE) == 0){
                maxPage = creditCardsAmount / MAX_ITEMS_IN_PAGE;
            } else {
                maxPage = creditCardsAmount / MAX_ITEMS_IN_PAGE + 1;
            }
            request.setAttribute(LAST_PAGE_PARAMETER_NAME, maxPage);
        } else {
            maxPage = (Integer) request.getAttribute(LAST_PAGE_PARAMETER_NAME);
        }
        return maxPage;
    }
}
