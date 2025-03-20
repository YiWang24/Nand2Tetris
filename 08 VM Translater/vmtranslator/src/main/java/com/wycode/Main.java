package com.wycode;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Objects;

public class Main {
    //VMTranslator
    //• Constructs a Parser to handle the input file;
    //• Constructs a CodeWriter to handle the output file;
    //• Iterates through the input file, parsing each line and generating
    //assembly code from it, using the services of the Parser and a CodeWriter.
    //Output: An assembly file named fileName.asm
    public static void main(String[] args) throws URISyntaxException {
        if (args.length == 0) {
            System.out.println("Usage: java Main <inputfile.vm>");
            return;
        }
        String inputFileName = args[0];
        File inputFile = new File(inputFileName);
        if (!inputFile.exists()) {
            System.out.println("Invalid file. Please provide a valid .vm file.");
            return;
        }
        String outputFileName;
        //create output file
        if (inputFile.isDirectory()) {
            outputFileName = inputFile.getName() + ".asm";
        } else {
            outputFileName = inputFileName.replace(".vm", ".asm");
        }
        File outputFile = new File(outputFileName);

        CodeWriter codeWriter = new CodeWriter(outputFile);

        if (inputFile.isDirectory()) {
            codeWriter.writeInit();
            for (File file : Objects.requireNonNull(inputFile.listFiles())) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".vm")) {
                    codeWriter.setFileName(file.getName());
                    translate(file, codeWriter);
                }
            }
        } else {
            translate(inputFile, codeWriter);
        }

        codeWriter.close();
        System.out.println("Translation complete: " + outputFile.getAbsolutePath());
    }

    public static void translate(File inputFileName, CodeWriter codeWriter) throws URISyntaxException {
        System.out.println("Translating file: " + inputFileName.getName());
        Parser parser = new Parser(inputFileName);

        while (parser.hasMoreLines()) {
            String commandType = parser.commandType();

            switch (commandType) {
                case "C_ARITHMETIC":
                    codeWriter.writeArithmetic(parser.arg1());
                    break;
                case "C_PUSH":
                case "C_POP":
                    codeWriter.writePushPop(commandType, parser.arg1(), parser.arg2());
                    break;
                case "C_LABEL":
                    codeWriter.writeLabel(parser.arg1());
                    break;
                case "C_GOTO":
                    codeWriter.writeGoto(parser.arg1());
                    break;
                case "C_IF":
                    codeWriter.writeIf(parser.arg1());
                    break;
                case "C_FUNCTION":
                    codeWriter.writeFunction(parser.arg1(), parser.arg2());
                    break;
                case "C_CALL":
                    codeWriter.writeCall(parser.arg1(), parser.arg2());
                    break;
                case "C_RETURN":
                    codeWriter.writeReturn();
                    break;
                default:
                    System.out.println("Invalid command. Please provide a valid command." + commandType);
            }
            parser.advance();
        }
    }
}