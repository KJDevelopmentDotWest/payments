package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import com.epam.jwd.controller.command.impl.LoginCommandImpl;
import com.epam.jwd.controller.command.impl.UserMainCommand;
import com.epam.jwd.dao.model.user.Role;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public enum ApplicationCommand {

    SIGNIN(new LoginCommandImpl()),
    USERMAIN(new UserMainCommand()),
    DEFAULT(defaultCommandImpl());

    private final Command command;
    private final List<Role> allowedRoles;

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
        return (request, response) -> {
            try {
                PrintWriter printWriter = response.getWriter();
                printWriter.print("There is no such command");
            } catch (IOException e) {
                //todo
            }
            //todo change to error page
            return new CommandResponse(request.getRequestURI(), false);
        };
    }
}
