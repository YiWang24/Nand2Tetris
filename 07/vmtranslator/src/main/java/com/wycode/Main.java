package com.wycode;

import java.io.File;
import java.net.URISyntaxException;

public class Main {
    //VMTranslator
    //• Constructs a Parser to handle the input file;
    //• Constructs a CodeWriter to handle the output file;
    //• Iterates through the input file, parsing each line and generating
    //assembly code from it, using the services of the Parser and a CodeWriter.
    //Output: An assembly file named fileName.asm
    public static void main(String[] args) throws URISyntaxException {
//        if (args.length == 0) {
//            System.out.println("Usage: java Main <inputfile.vm>");
//            return;
//        }
//        String inputFileName = args[0];
        String inputFileName = "test.vm";
        File inputFile = new File(inputFileName);
        if (!inputFile.exists() || !inputFileName.toLowerCase().endsWith(".vm")) {
            System.out.println("Invalid file. Please provide a valid .vm file.");
            return;
        }
        String outputFileName = inputFileName.replace(".vm", ".asm");
        Parser parser = new Parser(inputFileName);
        CodeWriter codeWriter = new CodeWriter(outputFileName);

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
                default:
                    System.out.println("Invalid command. Please provide a valid command." + commandType);
            }
            parser.advance();


        }
        codeWriter.close();
        System.out.println("Translation complete: " + outputFileName);


    }
}