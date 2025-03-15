package com.wycode;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author WY
 * @version 1.0
 **/

public class CodeWriter {
    private BufferedWriter writer;
    private int currentLine;
    //Opens an output file / stream and gets ready to write into it
    public CodeWriter(String filename) {
        try {
            writer = new BufferedWriter(new FileWriter(filename));
            currentLine = 0;
        } catch (IOException e) {
            System.err.println("Error opening file: " + filename);
            e.printStackTrace();
        }
    }

    //writes to the output file the assembly code that implements the given arithmetic logical command
    public void writeArithmetic(String command) {
        try {
            VmCommand vmCommand = VmCommand.valueOf(command);
            writer.write(vmCommand.getAssemblyCode("", currentLine++) + "\n");
        } catch (IOException e) {
            System.err.println("Error writing arithmetic command: " + command);
            e.printStackTrace();
        }
    }

    //writes to the output file the assembly code that implements the given push or pop command
    public void writePushPop(String command, String segment, int index) {
        try {
            VmCommand vmCommand = null;
            if (command.equals("C_PUSH")) {
                switch (segment) {
                    case "CONSTANT":
                        vmCommand = VmCommand.PUSH_CONSTANT;
                        break;
                    case "TEMP":
                        vmCommand = VmCommand.PUSH_TEMP;
                        break;
                    case "STATIC":
                        vmCommand = VmCommand.PUSH_STATIC;
                        break;
                    case "POINTER":
                        vmCommand = VmCommand.PUSH_POINTER;
                        index+=3;
                        break;
                    default:
                        vmCommand = VmCommand.PUSH;
                        break;
                }
            } else if (command.equals("C_POP")) {
                switch (segment) {
                    case "TEMP":
                        vmCommand = VmCommand.POP_TEMP;
                        break;
                    case "STATIC":
                        vmCommand = VmCommand.POP_STATIC;
                        break;
                    case "POINTER":
                        vmCommand = VmCommand.POP_POINTER;
                        index+=3;
                        break;
                    default:
                        vmCommand = VmCommand.POP;
                        break;
                }
            }
            assert vmCommand != null;
            writer.write(vmCommand.getAssemblyCode(segment, index) + '\n');
            currentLine++;
        } catch (IOException e) {
            System.err.println("Error writing push pop command: " + command);
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing file");
            e.printStackTrace();
        }
    }


}
