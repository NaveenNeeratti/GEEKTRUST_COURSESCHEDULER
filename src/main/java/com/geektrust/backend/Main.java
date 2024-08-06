package com.geektrust.backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import com.geektrust.backend.appconfig.ApplicationConfig;
import com.geektrust.backend.command.CommandInvoker;
import com.geektrust.backend.entity.Constant;
import com.geektrust.backend.exception.NoSuchCommandException;

public class Main {
	public static void main(String[] args) {
		List<String> commandLineArgs = new LinkedList<>(Arrays.asList(args));
        run(commandLineArgs);
        }
    public static void run(List<String> commandLineArgs) {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        CommandInvoker commandInvoker = applicationConfig.getCommandInvoker();
        BufferedReader reader;
        String inputFile = commandLineArgs.get(Constant.FILE_NAME);
        try {
            reader = new BufferedReader(new FileReader(inputFile));
            while (reader.ready()) {
                List<String> tokens = Arrays.asList(reader.readLine().split(" "));
                commandInvoker.executeCommand(tokens.get(Constant.COMMAND),tokens);
            }
            reader.close();
        } catch (IOException | NoSuchCommandException e) {
            e.printStackTrace();
        }
	}
}
