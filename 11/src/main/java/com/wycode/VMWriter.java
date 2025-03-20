package com.wycode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The `VMWriter` class is responsible for writing VM code to an output file.
 * It provider methods to generate VM commands for stack operations, arithmetic,
 * branching, function calls, and control flow.
 * <p>
 * This class is a key component of a jack compiler, helping translate jack language code into the hack VM language.
 *
 * @author WY
 * @version 1.0
 **/

public class VMWriter {
    private final FileWriter fileWriter;

    /**
     * Initializes the VMWriter and opens the output file for writing VM commands.
     *
     * @param file The output file where VM commands will be written.
     * @throws IOException If an error occurs while opening the file.
     */
    public VMWriter(File file) throws IOException {
        fileWriter = new FileWriter(file);
    }

    /**
     * Writes a VM push command to the output file.
     * Convert Jack variables segments to their corresponding VM segments.
     *
     * @param segment The memory segment to push from (e.g., "constant", "local", "this", "argument").
     * @param index The index in the segment to push from.
     * @throws IOException If an error occurs while writing to the file.
     */
    public void writePush(String segment, int index) throws IOException {
        //convert var to local
        if (segment.equals("var")) {
            segment = "local";
        }
        //convert field to this
        if (segment.equals("field")) {
            segment = "this";
        }
        String instruction = String.format("push %s %d\n", segment, index);
        fileWriter.write(instruction);
    }

    /**
     * Writes a pop command to the output file
     * Converts Jack variable segments to their corresponding VM segments.
     *
     * @param segment
     * @param index
     * @throws IOException
     */
    public void writePop(String segment, int index) throws IOException {
        if (segment.equals("var")) {
            segment = "local";
        }
        if (segment.equals("field")) {
            segment = "this";
        }
        String instruction = String.format("pop %s %d\n", segment, index);
        fileWriter.write(instruction);
    }

    /**
     * Writes a VM arithmetic command to the output file.
     * Converts Jack operators into their VM equivalents, handling special cases for multiplication and division.
     *
     * @param command
     * @throws IOException
     */
    public void writeArithmetic(String command) throws IOException {
        switch (command) {
            case "<":
                command = "lt";
                break;
            case ">":
                command = "gt";
                break;
            case "&":
                command = "and";
                break;
            case "+":
                command = "add";
                break;
            case "-":
                command = "sub";
                break;
            case "*":
                writeCall("Math.multiply", 2);
                return;
            case "/":
                writeCall("Math.divide", 2);
                return;
            case "=":
                command = "eq";
                break;
            case "|":
                command = "or";
                break;
            case "~":
                command = "not";
                break;
        }
        fileWriter.write(command + "\n");
    }

    /**
     * Writes a VM label command to define a label in the VM code.
     *
     * @param label
     * @throws IOException
     */
    public void writeLabel(String label) throws IOException {
        String instruction = String.format("label %s\n", label);
        fileWriter.write(instruction);
    }

    /**
     * Writes a VM goto command to jump to a specific label.
     *
     * @param label
     * @throws IOException
     */
    public void writeGoTo(String label) throws IOException {
        String instruction = String.format("goto %s\n", label);
        fileWriter.write(instruction);
    }

    /**
     * Writes a VM if-goto command for conditional branching.
     * If the topmost value on the stack is nonzero, jumps to the specified label.
     *
     * @param label
     * @throws IOException
     */
    public void writeIf(String label) throws IOException {
        String instruction = String.format("if-goto %s\n", label);
        fileWriter.write(instruction);
    }

    /**
     * Writes a VM call command to invoke a function.
     *
     * @param name
     * @param nArgs
     * @throws IOException
     */
    public void writeCall(String name, int nArgs) throws IOException {
        String instruction = String.format("call %s %d\n", name, nArgs);
        fileWriter.write(instruction);
    }

    /**
     * Writes a VM function declaration.
     *
     * @param name
     * @param nArgs
     * @throws IOException
     */
    public void writeFunction(String name, int nArgs) throws IOException {
        String instruction = String.format("function %s %d\n", name, nArgs);
        fileWriter.write(instruction);
    }

    /**
     * Writes a VM return command to return from a function.
     *
     * @throws IOException
     */
    public void writeReturn() throws IOException {
        fileWriter.write("return\n");
    }

    /**
     * Closes the output file when VM code generation is complete.
     *
     * @throws IOException
     */
    public void close() throws IOException {
        fileWriter.close();
    }
}
