package com.wycode;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Usage: java Main <input-file> <output-file>");
            System.exit(1);
        }
        String inputFileName = args[0];
        File inputFile = new File(inputFileName);
        processFileOrDirectory(inputFile);
    }

    private static void processFileOrDirectory(File inputFile) throws IOException {
        if (inputFile.isDirectory()) {
            File[] files = inputFile.listFiles();
            if (files == null) {
                System.out.println("Error: Unable to read directory " + inputFile.getName());
                return;
            }
            // compile every jack files in the directory
            for (File file : files) {
                if (file.getName().endsWith(".jack")) {
                    compile(file);
                }
            }
        }
        // compile single file
        else if (inputFile.isFile() && inputFile.getName().endsWith(".jack")) {
            compile(inputFile);
        }
        // file is not exist
        else {
            System.out.println("Error: " + inputFile.getName() + " is not a .jack file or a directory.");
        }
    }

    private static void compile(File inputFile) throws IOException {
        File parentDir = inputFile.getParentFile();
        if (parentDir == null) {
            parentDir = new File(".");
        }
        File outputDir = new File(parentDir, "output");
        if (!outputDir.exists()) {
            outputDir.mkdir();
        }
        String outputFileName = inputFile.getName().replace(".jack", ".vm");
        File outputFile = new File(outputDir, outputFileName);

        CompilationEngine compilationEngine = new CompilationEngine(inputFile, outputFile);
        compilationEngine.compileClass();

        System.out.println("Compiled: " + inputFile.getName() + " → " + outputFile.getName());

    }


}