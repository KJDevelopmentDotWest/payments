package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.impl.action.*;
import com.epam.jwd.controller.command.impl.showpage.*;
import com.epam.jwd.dao.model.user.Role;

import java.util.Arrays;
import java.util.List;

public enum ApplicationCommand {

    SHOW_SIGNIN(new ShowSigninCommand()),
    SHOW_ACCOUNT(new ShowAccountCommand()),
    SHOW_ADMIN_USERS(new ShowAdminUsersCommand(), Role.ADMIN),
    SHOW_CREDIT_CARDS(new ShowCreditCardsCommand(), Role.CUSTOMER),
    SHOW_ADMIN_CREDIT_CARDS(new ShowAdminCreditCardsCommand(), Role.ADMIN),
    SHOW_PAYMENTS(new ShowPaymentsCommand(), Role.CUSTOMER),
    SHOW_ADMIN_PAYMENTS(new ShowAdminPaymentsCommand(), Role.ADMIN),
    SHOW_ADD_CREDIT_CARD(new ShowAddCreditCardCommand(), Role.CUSTOMER),
    SHOW_CREATE_PAYMENT(new ShowCreatePaymentCommand(), Role.CUSTOMER),
    SHOW_CREATE_ACCOUNT(new ShowCreateAccountCommand(), Role.CUSTOMER),
    SHOW_CREATE_USER(new ShowCreateUserCommand()),
    SHOW_CHECKOUT(new ShowCheckoutCommand(), Role.CUSTOMER),
    SHOW_ERROR(new ShowErrorPageCommand()),
    SHOW_EDIT_PAYMENT(new ShowEditPaymentCommand(), Role.CUSTOMER),
    SHOW_EDIT_ACCOUNT(new ShowEditAccountCommand(), Role.CUSTOMER),

    SIGNIN(new SigninCommand()),
    SIGNOUT(new SignoutCommand()),
    CHANGE_LANGUAGE(new ChangeLanguageCommand()),
    COMMIT_PAYMENT_CREATION(new CommitPaymentCreationCommand(), Role.CUSTOMER),
    COMMIT_PAYMENT_CHANGES(new CommitPaymentChangesCommand(), Role.CUSTOMER),
    COMMIT_ACCOUNT_CHANGES(new CommitAccountChangesCommand(), Role.CUSTOMER),
    COMMIT_ACCOUNT_CREATION(new CommitAccountCreationCommand(), Role.CUSTOMER),
    COMMIT_USER_CREATION(new CommitUserCreationCommand()),
    ADD_CREDIT_CARD(new CommitCreditCardAddingCommand(), Role.CUSTOMER),
    BLOCK_CREDIT_CARD(new BlockCreditCardCommand(), Role.CUSTOMER),
    BLOCK_USER(new BlockUserCommand(), Role.CUSTOMER),
    UNBLOCK_CREDIT_CARD(new UnblockCreditCardCommand(), Role.ADMIN),
    UNBLOCK_USER(new UnblockUserCommand(), Role.ADMIN),
    ADD_FUNDS(new AddFundsCommand(), Role.CUSTOMER),
    CHECKOUT_PAYMENT(new CheckoutPaymentCommand(), Role.CUSTOMER),
    DEFAULT(defaultCommandImpl());

    private final Command command;
    private final List<Role> allowedRoles;

    public static final String SHOW_ERROR_PAGE_URL = "/payments?command=show_error";

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

    public List<Role> getAllowedRoles(){
        return allowedRoles;
    }

    private static Command defaultCommandImpl(){
        return SHOW_ERROR.getCommand();
    }
}
