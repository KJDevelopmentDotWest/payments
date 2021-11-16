package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.impl.*;
import com.epam.jwd.controller.command.impl.showpage.*;
import com.epam.jwd.dao.model.user.Role;

import java.util.Arrays;
import java.util.List;

public enum ApplicationCommand {

    SHOW_SIGNIN(new ShowSigninPage()),
    SHOW_ACCOUNT(new ShowAccountCommand()),
    SHOW_CREDIT_CARDS(new ShowCreditCardsCommand(), Role.CUSTOMER),
    SHOW_PAYMENTS(new ShowPaymentsCommand(), Role.CUSTOMER),
    SHOW_ADMIN_PAYMENTS(new ShowAdminPaymentsCommand(), Role.ADMIN),
    SHOW_ADD_CREDIT_CARD(new ShowAddCreditCardCommand()),
    SHOW_CREATE_PAYMENT(new ShowCreatePaymentCommand()),
    SHOW_CREATE_ACCOUNT(new ShowCreateAccountCommand()),
    SHOW_CREATE_USER(new ShowCreateUserCommand()),
    SHOW_CHECKOUT(new ShowCheckoutCommand()),
    SHOW_ERROR(new ShowErrorPageCommand()),
    SHOW_EDIT_PAYMENT(new ShowEditPaymentCommand()),
    SHOW_EDIT_ACCOUNT(new ShowEditAccountCommand()),

    SIGNIN(new SigninCommand()),
    SIGNOUT(new SignoutCommand()),
    COMMIT_PAYMENT_CREATION(new CommitPaymentCreationCommand()),
    COMMIT_PAYMENT_CHANGES(new CommitPaymentChangesCommand()),
    COMMIT_ACCOUNT_CHANGES(new CommitAccountChangesCommand()),
    COMMIT_ACCOUNT_CREATION(new CommitAccountCreationCommand()),
    COMMIT_USER_CREATION(new CommitUserCreationCommand()),
    ADD_CREDIT_CARD(new AddCreditCardCommand()),
    BLOCK_CREDIT_CARD(new BlockCreditCardCommand()),
    ADD_FUNDS(new AddFundsCommand()),
    CHECKOUT_PAYMENT(new CheckoutPaymentCommand()),
    DEFAULT(defaultCommandImpl());

    private final Command command;
    private final List<Role> allowedRoles;

    private static final String SHOW_ERROR_PAGE_URL = "/payments?command=show_error";

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
        return SHOW_ERROR.getCommand();
    }
}
