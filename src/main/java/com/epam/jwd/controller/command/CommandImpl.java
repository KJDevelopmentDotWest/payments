package com.epam.jwd.controller.command;

import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.UserService;

import java.util.Arrays;

public enum CommandImpl {

    LoginCommand(loginCommandImpl()),
    DefaultCommand(defaultCommandImpl());

    private final Command command;

    private CommandImpl(Command command){
        this.command = command;
    }

    public Command getCommand(){
        return command;
    }

    public static CommandImpl getByString(String commandString){
        return Arrays.stream(CommandImpl.values())
                .filter(command -> command.toString()
                        .equalsIgnoreCase(commandString))
                .findFirst()
                .orElse(DefaultCommand);
    }

    private static Command loginCommandImpl(){
        return () -> {
            Service<UserDto, Integer> service = new UserService();
            try {
                return service.getAll().toString();
            } catch (ServiceException e) {
                return "no users found";
            }
        };
    }

    private static Command defaultCommandImpl(){
        return () -> {
            return "There is no such command";
        };
    }
}
