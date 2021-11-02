package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.commandresponse.CommandResponse;
import com.epam.jwd.controller.command.impl.LoginCommandImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public enum ApplicationCommand {

    LOGIN(new LoginCommandImpl()),
    DEFAULT(defaultCommandImpl());

    private final Command command;

    ApplicationCommand(Command command){
        this.command = command;
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
