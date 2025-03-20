package com.wycode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author WY
 * @version 1.0
 **/

public class CodeWriter {
    private BufferedWriter writer;
    private int currentLine;
    private String fileName;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    //Opens an output file / stream and gets ready to write into it
    public CodeWriter(File file) {
        write(file);
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
                        writer.write("// push static " + index + "\n"); // write a comment for readability
                        writer.write("@" + fileName + "." + index + "\n"); // load the static variable into the A register
                        writer.write("D=M\n"); // D = filename.i
                        writer.write("@SP\n"); // load the stack pointer into the A register
                        writer.write("A=M\n"); // point to the top of the stack
                        writer.write("M=D\n"); // push filename.i onto the stack
                        writer.write("@SP\n"); // load the stack pointer into the A register
                        writer.write("M=M+1\n"); // increment the stack pointer
                        currentLine++;
                        return;
                    case "POINTER":
                        vmCommand = VmCommand.PUSH_POINTER;
                        index += 3;
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
                        writer.write("// pop static " + index + "\n"); // write a comment for readability
                        writer.write("@SP\n"); // load the stack pointer into the A register
                        writer.write("AM=M-1\n"); // decrement SP and point to the top of the stack
                        writer.write("D=M\n"); // D = *SP
                        writer.write("@" + fileName + "." + index + "\n"); // load the static variable into the A register
                        writer.write("M=D\n"); // filename.i = *SP
                        currentLine++;
                        return;
                    case "POINTER":
                        vmCommand = VmCommand.POP_POINTER;
                        index += 3;
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


    public void writeLabel(String label) {
        try {
            VmCommand vmCommand = VmCommand.LABEL;
            writer.write(vmCommand.getAssemblyCode(label, 0) + "\n");
            currentLine++;
        } catch (IOException e) {
            System.err.println("Error writing label: " + label);
        }
    }

    public void writeGoto(String label) {
        try {
            VmCommand vmCommand = VmCommand.GOTO;
            writer.write(vmCommand.getAssemblyCode(label, 0) + "\n");
            currentLine++;
        } catch (IOException e) {
            System.err.println("Error writing label: " + label);
        }
    }

    public void writeIf(String label) {
        try {
            VmCommand vmCommand = VmCommand.IF_GOTO;
            writer.write(vmCommand.getAssemblyCode(label, 0) + "\n");
            currentLine++;
        } catch (IOException e) {
            System.err.println("Error writing label: " + label);
        }
    }

    public void writeFunction(String functionName, int nVars) {

        try {
            String push = "@SP\n" +
                    "A=M\n" +
                    "M=0\n" +
                    "@SP\n" +
                    "M=M+1\n";
            writer.write("(" + functionName + ")\n" + push.repeat(Math.max(0, nVars)) + "\n");
            currentLine++;
        } catch (IOException e) {
            System.err.println("Error writing function: " + functionName);
        }
    }

    public void writeCall(String functionName, int nArgs) {
        try {
            VmCommand vmCommand = VmCommand.CALL;
            writer.write(vmCommand.getAssemblyCode(functionName, currentLine, nArgs) + "\n");
            currentLine++;
        } catch (IOException e) {
            System.err.println("Error writing Call: " + functionName);
        }
    }

    public void writeReturn() {
        try {
            VmCommand vmCommand = VmCommand.RETURN;
            writer.write(vmCommand.getAssemblyCode("", 0, currentLine) + "\n");
            currentLine++;
        } catch (IOException e) {
            System.err.println("Error writing RETURN: ");
        }
    }

    public void writeInit() {
        try {
            VmCommand vmCommand = VmCommand.INIT;
            writer.write(vmCommand.getAssemblyCode("", 0, 0) + "\n");
            writeCall("SYS.INIT", 0);
            currentLine++;
        } catch (IOException e) {
            System.err.println("Error writing RETURN: ");
        }
    }

    private void write(File file) {
        try {
            writer = new BufferedWriter(new FileWriter(file));
            currentLine = 0;
        } catch (IOException e) {
            System.err.println("Error opening file: " + file.getName());
            e.printStackTrace();
        }
    }
}
