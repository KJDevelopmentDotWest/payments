package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.impl.LoginCommandImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public enum CommandList {

    LoginCommand(new LoginCommandImpl()),
    DefaultCommand(defaultCommandImpl());

    private final Command command;

    CommandList(Command command){
        this.command = command;
    }

    public Command getCommand(){
        return command;
    }

    public static CommandList getByString(String commandString){
        return Arrays.stream(CommandList.values())
                .filter(command -> command.toString()
                        .equalsIgnoreCase(commandString))
                .findFirst()
                .orElse(DefaultCommand);
    }

    private static Command defaultCommandImpl(){
        return (request, response) -> {
            try {
                PrintWriter printWriter = response.getWriter();
                printWriter.print("There is no such command");
            } catch (IOException e) {
                //todo
            }
        };
    }
}
