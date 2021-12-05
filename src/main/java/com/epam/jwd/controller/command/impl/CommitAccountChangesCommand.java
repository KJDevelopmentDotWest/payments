package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import com.epam.jwd.controller.command.impl.showpage.ShowEditPaymentCommand;
import com.epam.jwd.service.dto.userdto.AccountDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.AccountService;
import com.epam.jwd.service.impl.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommitAccountChangesCommand implements Command {

    private static final Logger logger = LogManager.getLogger(CommitAccountChangesCommand.class);

    private static final String SHOW_ACCOUNT_PAGE_URL = "/payments?command=show_account";

    private static final String NAME_PARAMETER_NAME = "name";
    private static final String SURNAME_PARAMETER_NAME = "surname";
    private static final String PICTURE_ID_PARAMETER_NAME = "pictureId";

    AccountService accountService = new AccountService();
    UserService userService = new UserService();

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("command " + CommitAccountChangesCommand.class);

        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute(ID_ATTRIBUTE_NAME);
        String name = request.getParameter(NAME_PARAMETER_NAME);
        String surname = request.getParameter(SURNAME_PARAMETER_NAME);
        Integer pictureId;
        try{
            pictureId = Integer.valueOf(request.getParameter(PICTURE_ID_PARAMETER_NAME));
        } catch ( NumberFormatException e){
            logger.error(e);
            pictureId = -1;
        }

        try {
            AccountDto account = accountService.getById(
                    userService.getById(userId).getAccountId()
            );
            account.setName(name);
            account.setSurname(surname);
            if (pictureId != -1){
                account.setProfilePictureId(pictureId);
            }
            accountService.update(account);
        } catch (ServiceException e) {
            logger.error(e.getErrorCode());
        }

        return new CommandResponse(request.getContextPath() + SHOW_ACCOUNT_PAGE_URL, true);
    }
}
