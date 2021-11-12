package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import com.epam.jwd.controller.command.impl.*;
import com.epam.jwd.dao.model.user.Role;

import java.util.Arrays;
import java.util.List;

public enum ApplicationCommand {

    SIGNIN(new SigninCommand()),
    SIGNOUT(new SignoutCommand()),
    PAYMENTS(new PaymentsCommand(), Role.CUSTOMER),
    CREDIT_CARDS(new CreditCardsCommand(), Role.CUSTOMER),
    COMMIT_PAYMENT_CREATION(new CommitPaymentCreationCommand()),
    EDIT_PAYMENT(new EditPaymentCommand()),
    COMMIT_PAYMENT_CHANGES(new CommitPaymentChangesCommand()),
    ADD_CREDIT_CARD(new AddCreditCardCommand()),
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
