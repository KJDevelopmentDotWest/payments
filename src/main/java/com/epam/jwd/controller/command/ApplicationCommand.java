package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import com.epam.jwd.controller.command.impl.*;
import com.epam.jwd.controller.command.impl.showpage.ShowAccountCommand;
import com.epam.jwd.controller.command.impl.showpage.ShowCreditCardsCommand;
import com.epam.jwd.controller.command.impl.showpage.ShowPaymentsCommand;
import com.epam.jwd.controller.command.impl.showpage.ShowSigninPage;
import com.epam.jwd.dao.model.user.Role;

import java.util.Arrays;
import java.util.List;

public enum ApplicationCommand {

    SHOW_SIGNIN(new ShowSigninPage()),
    SHOW_ACCOUNT(new ShowAccountCommand()),
    SIGNIN(new SigninCommand()),
    SIGNOUT(new SignoutCommand()),
    SHOW_PAYMENTS(new ShowPaymentsCommand(), Role.CUSTOMER),
    SHOW_CREDIT_CARDS(new ShowCreditCardsCommand(), Role.CUSTOMER),
    COMMIT_PAYMENT_CREATION(new CommitPaymentCreationCommand()),
    EDIT_PAYMENT(new EditPaymentCommand()),
    COMMIT_PAYMENT_CHANGES(new CommitPaymentChangesCommand()),
    ADD_CREDIT_CARD(new AddCreditCardCommand()),
    BLOCK_CREDIT_CARD(new BlockCreditCardCommand()),
    ADD_FUNDS(new AddFundsCommand()),
    CHECKOUT_PAYMENT(new CheckoutPaymentCommand()),
    DEFAULT(defaultCommandImpl());

    private final Command command;
    private final List<Role> allowedRoles;

    private static final String ERROR_PAGE_URL = "/jsp/errorpage.jsp";

    ApplicationCommand(Command command, Role... roles){
        this.command = command;
        this.allowedRoles = List.of(roles);
    }

    public Command getCommand(){
        return command;
    }

    public static ApplicationCommand getByString(String commandString){
        return Arrays.stream(ApplicationCommand.values())
                .filter(command -> command.toString()
                        .equalsIgnoreCase(commandString))
                .findFirst()
                .orElse(DEFAULT);
    }

    private List<Role> getAllowedRoles(){
        return allowedRoles;
    }

    private static Command defaultCommandImpl(){
        return (request, response) ->
                new CommandResponse(request.getContextPath() + ERROR_PAGE_URL, true);
    }
}
