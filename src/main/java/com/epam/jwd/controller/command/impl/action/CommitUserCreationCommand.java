package com.epam.jwd.controller.command.impl.action;

import com.epam.jwd.controller.command.ApplicationCommand;
import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import com.epam.jwd.dao.model.user.Role;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.exception.ExceptionCode;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.UserService;
import com.epam.jwd.service.security.Security;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class CommitUserCreationCommand implements Command {

    private static final Logger logger = LogManager.getLogger(CommitUserCreationCommand.class);

    private static final String SHOW_SIGNIN_PAGE_URL = "/payments?command=show_signin";
    private static final String SHOW_CREATE_USER_PAGE_URL = "/payments?command=show_create_user";

    private static final String INCORRECT_ATTRIBUTE_NAME = "incorrect";
    private static final String SUCCESS_ATTRIBUTE_NAME = "success";
    private static final String LOGIN_PARAMETER_NAME = "login";
    private static final String PASSWORD_PARAMETER_NAME = "password";

    private static final String LANG_ATTRIBUTE_NAME = "lang";
    private static final String LOCALE_BUNDLE_NAME = "locale";
    private static final String USER_CREATE_SUCCESS_PROPERTY_NAME = "usercreatingsuccess";

    private static final String LOGIN_NOT_UNIQUE_MESSAGE = "User with provided login already exists";
    private static final String SOMETHING_WENT_WRONG_MESSAGE = "Something went wrong, user was not created";

    private final UserService service = new UserService();

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {

        logger.info("command " + CommitUserCreationCommand.class);

        String login = request.getParameter(LOGIN_PARAMETER_NAME);
        String password = request.getParameter(PASSWORD_PARAMETER_NAME);

        if (Objects.isNull(login) || Objects.isNull(password)){
            logger.error("required data not exists");
            return new CommandResponse(request.getContextPath() + ApplicationCommand.SHOW_ERROR_PAGE_URL, true);
        }

        UserDto userDto = new UserDto(login, Security.getInstance().generateHashForPassword(password), null, true, Role.CUSTOMER);

        try {
            service.create(userDto);
            sendMessage(request);
            return new CommandResponse(request.getContextPath() + SHOW_SIGNIN_PAGE_URL, true);
        } catch (ServiceException e) {
            logger.error(e.getErrorCode());
            if (e.getErrorCode() == ExceptionCode.USER_LOGIN_IS_NOT_UNIQUE_EXCEPTION_CODE){
                request.getSession().setAttribute(INCORRECT_ATTRIBUTE_NAME, LOGIN_NOT_UNIQUE_MESSAGE);
                return new CommandResponse(request.getContextPath() + SHOW_CREATE_USER_PAGE_URL, true);
            }
            if (e.getErrorCode() == ExceptionCode.USER_WAS_NOT_CREATED_EXCEPTION_CODE){
                request.getSession().setAttribute(INCORRECT_ATTRIBUTE_NAME, SOMETHING_WENT_WRONG_MESSAGE);
                return new CommandResponse(request.getContextPath() + SHOW_CREATE_USER_PAGE_URL, true);
            }
        }

        return new CommandResponse(request.getContextPath() + SHOW_SIGNIN_PAGE_URL, true);
    }

    private void sendMessage(HttpServletRequest request){
        request.getSession().removeAttribute(INCORRECT_ATTRIBUTE_NAME);
        String localeName = (String) request.getSession().getAttribute(LANG_ATTRIBUTE_NAME);
        ResourceBundle locale;
        if (!Objects.isNull(localeName)){
            locale = ResourceBundle.getBundle(LOCALE_BUNDLE_NAME, new Locale(localeName));
        } else {
            locale = ResourceBundle.getBundle(LOCALE_BUNDLE_NAME, Locale.US);
        }
        request.getSession().setAttribute(SUCCESS_ATTRIBUTE_NAME, locale.getString(USER_CREATE_SUCCESS_PROPERTY_NAME));
    }
}
