package com.wycode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author WY
 * @version 1.0
 **/

//Handles the parsing of a single .vm file
//Reads a VM command, parses the command into its lexical components,
//and provides convenient access to these components
//Ignores white space and comments
public class Parser {
    private List<String> cleanCommands;
    private int currentLine;

    //Constructor / initializer: Creates a Parser and opens the input (source VM code) file
    public Parser(String fileName) {
        cleanCommands = new ArrayList<>();
        currentLine = 0;
        readCommands(fileName);
    }

    //Checks if there is more work to do (boolean)
    public boolean hasMoreLines() {
        return currentLine < cleanCommands.size();
    }

    //Gets the next command and makes it the current instruction (string)
    public void advance() {
            currentLine++;
    }

    //Returns the type of the current command (a string constant):
    //C_ARITHMETIC if the current command is an arithmetic-logical command;
    //C_PUSH, C_POP if the current command is one of these command types
    public String commandType() {
        if (getCurrentCommand() == null) {
            return null;
        }
        String command = getCurrentCommand()[0].toLowerCase();
        switch (command) {
            case "push":
                return "C_PUSH";
            case "pop":
                return "C_POP";
            case "label":
                return "C_LABEL";
            case "goto":
                return "C_GOTO";
            case "if-goto":
                return "C_IF";
            case "function":
                return "C_FUNCTION";
            case "return":
                return "C_RETURN";
            case "call":
                return "C_CALL";
            default:
                return "C_ARITHMETIC";
        }
    }

    //Returns the first argument of the current command;
    //In the case of C_ARITHMETIC, the command itself is returned (string)
    public String arg1() {
        if (commandType().equals("C_RETURN")) {
            return null;
        }
        String[] currentCommand = getCurrentCommand();
        assert currentCommand != null;
        if(commandType().equals("C_ARITHMETIC")) {
            return currentCommand[0].toUpperCase();
        }
        String segment = currentCommand[1].toUpperCase();
        return switch(segment){
            case "LOCAL" -> "LCL";
            case "ARGUMENT" -> "ARG";
            default -> segment;
        };
    }

    //Returns the second argument of the current command (int);
    //Called only if the current command is C_PUSH, C-POP, C_FUNCTION, or C_CALL
    public int arg2() {
        if (!Arrays.asList("C_PUSH", "C_POP", "C_FUNCTION", "C_CALL").contains(commandType())) {
            throw new IllegalStateException("arg2() called on wrong command type.");
        }
        return Integer.parseInt(Objects.requireNonNull(getCurrentCommand())[2]);
    }


    //get current commend;
    private String[] getCurrentCommand() {
        if (hasMoreLines()) {
            return cleanCommands.get(currentLine).split(" ");
        }
        return null;
    }


    //read and clean file
    private void readCommands(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.split("//")[0].trim();
                if (!line.isEmpty()) {
                    line = line.replaceAll("\\s+", " ");
                    cleanCommands.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + fileName);
            e.printStackTrace();
        }
    }


}
