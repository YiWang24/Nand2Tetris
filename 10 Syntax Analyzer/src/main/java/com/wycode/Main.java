package com.wycode;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class Main {
    public static void main(String[] args) throws IOException {
//        if (args.length == 0){
//            System.out.println("Usage: java Main <input-file> <output-file>");
//            System.exit(1);
//        }
//        String inputFileName = args[0];
        String inputFileName = "ExpressionLessSquare";
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
            for (File file : files) {
                if (file.getName().endsWith(".jack")) {
                    compile(file);
                }
            }
        } else if (inputFile.isFile() && inputFile.getName().endsWith(".jack")) {
            compile(inputFile);
        } else {
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
            outputDir.mkdirs();
        }
        String outputFileName = inputFile.getName().replace(".jack", ".xml");
        File outputFile = new File(outputDir, outputFileName);

        CompilationEngine compilationEngine = new CompilationEngine(inputFile, outputFile);
        compilationEngine.compileClass();

        System.out.println("Compiled: " + inputFile.getName() + " → " + outputFile.getName());

        boolean result = areXmlFilesIdentical(outputDir, outputFileName);
        System.out.println("Are XML files identical (ignoring spaces)? " + result + "\n");
    }

    public static boolean areXmlFilesIdentical(File outputDir, String fileName) throws IOException {

        File outputFile = new File(outputDir, fileName);


        File parentFile = new File(outputDir.getParentFile(), fileName);


        if (!outputFile.exists() || !parentFile.exists()) {
            System.out.println("File is not exist: " + outputFile.getAbsolutePath() + " OR " + parentFile.getAbsolutePath());
            return false;
        }

        String outputContent = new String(Files.readAllBytes(outputFile.toPath()), StandardCharsets.UTF_8)
                .replaceAll("\\s+", "");
        String parentContent = new String(Files.readAllBytes(parentFile.toPath()), StandardCharsets.UTF_8)
                .replaceAll("\\s+", "");


        return outputContent.equals(parentContent);
    }

}