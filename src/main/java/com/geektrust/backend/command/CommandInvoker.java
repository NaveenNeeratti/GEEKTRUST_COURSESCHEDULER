package com.geektrust.backend.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.geektrust.backend.exception.NoSuchCommandException;

public class CommandInvoker {

    private final Map<String,ICommand> commandMap = new HashMap<>();

    public void registerCommand(String commandName,ICommand command){
        commandMap.put(commandName,command);
    }

    public ICommand getCommand(String commandName){
        return commandMap.get(commandName);
    }

    public void executeCommand(String commandName,List<String> tokens) throws NoSuchCommandException{
        ICommand command = commandMap.get(commandName);
        if(command == null){
            throw new NoSuchCommandException();
        }
        command.execute(tokens);
    }
}
