package com.wycode;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.function.Predicate;
import java.util.function.Supplier;


/**
 * The CompilationEngine class parses the Jack code, processes tokens from the JackTokenizer,
 * and writes VM code to the VMWriter.
 * Each compilation method handles specific grammar rules (like class definition, function declarations, etc.),
 * advancing the tokenizer and emitting the corresponding VM instructions.
 *
 * @author WY
 * @version 1.0
 **/
@FunctionalInterface
interface RunnableWithException {
    void run() throws IOException;
}

public class CompilationEngine {
    private final JackTokenizer jackTokenizer; // Tokenizer to process Jack code
    private final SymbolTable symbolTable; // Symbol table for storing variable and function symbols
    private final VMWriter vmWriter; // Writer to generate VM code
    private String className; // Name of the current class
    private int labelIndex; // Label index for creating unique labels

    public CompilationEngine(File inputFile, File outputFile) throws IOException {
        jackTokenizer = new JackTokenizer(inputFile); // Initialize the Jack tokenizer with the input file
        symbolTable = new SymbolTable(); // Initialize the symbol table
        vmWriter = new VMWriter(outputFile); // Initialize the VM writer with the output file
        labelIndex = 0; // Initialize the label index
    }

    // Compile a class declaration
    public void compileClass() throws IOException {
        // Skip the 'class' keyword
        jackTokenizer.advance();
        // Read and store the class name
        className = getCurrentToken(jackTokenizer::identifier);
        // Process the class body (variable declarations and method declarations)
        processWithParentheses(() -> {
            compileClassVarDec(); // Compile class variables (static and field)
            compileSubroutine(); // Compile subroutines (methods, constructors, functions)
        });
        vmWriter.close(); // Close the VM writer after compilation
    }

    // Compile class variable declarations (static and field)
    public void compileClassVarDec() {
        processVariable((jackTokenizer) -> jackTokenizer.isKeyword("static") || jackTokenizer.isKeyword("field"));
    }

    // Compile subroutine declarations (method, function, or constructor)
    public void compileSubroutine() throws IOException {
        while ((jackTokenizer.isKeyword("constructor") ||
                jackTokenizer.isKeyword("function") ||
                jackTokenizer.isKeyword("method"))) {
            // Skip the subroutine keyword (constructor, function, or method)
            String keyword = getCurrentToken(jackTokenizer::keyWord);
            // Skip the return type (type or identifier)
            jackTokenizer.advance();
            // Read the subroutine name
            String functionName = getCurrentToken(jackTokenizer::identifier);
            // Reset the symbol table for each subroutine
            symbolTable.reset();
            // If it's a method, define 'this' as an argument
            if (keyword.equals("method")) {
                symbolTable.define("this", className, "argument");
            }
            // Compile the parameter list
            processWithParentheses(this::compileParameterList);
            // Compile the subroutine body
            compileSubroutineBody(keyword, functionName);
        }
    }

    // Compile the parameter list of a subroutine
    public void compileParameterList() {
        processSymbolTable("", "argument", true);
    }

    // Compile the body of a subroutine (variable declarations, function call, etc.)
    public void compileSubroutineBody(String method, String functionName) throws IOException {
        processWithParentheses(() -> {
            compileVarDec();// Compile local variable declarations
            // Write the function declaration to the VM
            vmWriter.writeFunction(className + "." + functionName, symbolTable.varCount("var"));
            // Handle method-specific initialization (e.g., 'this' pointer for methods, memory allocation for constructors)
            if (method.equals("method")) {
                vmWriter.writePush("argument", 0);
                vmWriter.writePop("pointer", 0);
            } else if (method.equals("constructor")) {
                vmWriter.writePush("constant", symbolTable.varCount("field"));
                vmWriter.writeCall("Memory.alloc", 1);
                vmWriter.writePop("pointer", 0);
            }
            compileStatements();// Compile statements inside the subroutine body
        });
    }

    // Compile variable declarations
    public void compileVarDec() {
        processVariable((jackTokenizer) -> jackTokenizer.isKeyword("var"));
    }

    // Compile statements inside the subroutine or class body
    public void compileStatements() throws IOException {
        while (jackTokenizer.isKeyword()) {
            switch (jackTokenizer.keyWord()) {
                case "let":
                    compileLet();
                    break;
                case "if":
                    compileIf();
                    break;
                case "while":
                    compileWhile();
                    break;
                case "do":
                    compileDo();
                    break;
                case "return":
                    compileReturn();
                    break;
            }
        }

    }

    // Compile 'let' statement
    public void compileLet() throws IOException {
        // skip let
        jackTokenizer.advance();
        // Read the variable name
        String varName = getCurrentToken(jackTokenizer::identifier);
        boolean isArray = false;
        // Check if it's an array
        if (jackTokenizer.isSymbol('[')) {
            isArray = true;
            // Compile array expression
            processWithParentheses(() -> {
                compileExpression();
                vmWriter.writePush(symbolTable.kindOf(varName), symbolTable.indexOf(varName));
                vmWriter.writeArithmetic("+");
            });
        }
        // Assign expression to the variable
        jackTokenizer.advance(); // skip =
        compileExpression();
        jackTokenizer.advance();  // skip ;
        if (isArray) {
            vmWriter.writePop("temp", 0);
            vmWriter.writePop("pointer", 1);
            vmWriter.writePush("temp", 0);
            vmWriter.writePop("that", 0);
        } else {
            vmWriter.writePop(symbolTable.kindOf(varName), symbolTable.indexOf(varName));
        }
    }

    // Compile 'if' statement
    public void compileIf() throws IOException {
        // skip if
        jackTokenizer.advance();
        // Compile expression
        processWithParentheses(this::compileExpression);
        vmWriter.writeArithmetic("not");
        String L1 = className + "_" + labelIndex++;
        String L2 = className + "_" + labelIndex++;
        vmWriter.writeIf(L2);
        // // Compile statement block
        processWithParentheses(this::compileStatements);
        vmWriter.writeGoTo(L1);
        vmWriter.writeLabel(L2);
        // Compile 'else' statement block if present
        if (jackTokenizer.isKeyword("else")) {
            // skip else
            jackTokenizer.advance();
            // {statement}
            processWithParentheses(this::compileStatements);
        }
        vmWriter.writeLabel(L1);
    }

    // Compile 'while' statement
    public void compileWhile() throws IOException {
        String L1 = className + "_" + labelIndex++;
        String L2 = className + "_" + labelIndex++;
        // skip While
        jackTokenizer.advance();
        // Compile expression
        vmWriter.writeLabel(L1);
        processWithParentheses(this::compileExpression);
        vmWriter.writeArithmetic("not");
        vmWriter.writeIf(L2);
        // Compile statement block
        processWithParentheses(this::compileStatements);
        vmWriter.writeGoTo(L1);
        vmWriter.writeLabel(L2);

    }

    // Compile 'do' statement
    public void compileDo() throws IOException {
        // skip Do
        jackTokenizer.advance();
        // handle function call
        processCallFunction();
        jackTokenizer.advance(); // skip ;
        vmWriter.writePop("temp", 0);
    }

    // Compile 'return' statement
    public void compileReturn() throws IOException {
        // skip return
        jackTokenizer.advance();
        if (!jackTokenizer.isSymbol(';')) {
            compileExpression();
        } else {
            vmWriter.writePush("constant", 0);
        }
        // skip ;
        jackTokenizer.advance();
        vmWriter.writeReturn();
    }

    // Compile expression (terms and operators)
    public void compileExpression() throws IOException {
        compileTerm();
        while (jackTokenizer.isOp()) {
            String op = getCurrentToken(jackTokenizer::symbol).toString();
            compileTerm();
            vmWriter.writeArithmetic(op);
        }
    }

    // Compile a term (literal value, variable, function call, etc.)
    public void compileTerm() throws IOException {
        // Check if the current token is an integer constant
        if (jackTokenizer.isIntVal()) {
            Integer intToken = getCurrentToken(jackTokenizer::intVal);
            vmWriter.writePush("constant", intToken);
        }
        // Check if the current token is a string constant
        else if (jackTokenizer.isStringVal()) {
            String stringToken = getCurrentToken(jackTokenizer::stringVal);
            // Push the string length to the stack
            vmWriter.writePush("constant", stringToken.length());
            // Call String.new to create a new string object
            vmWriter.writeCall("String.new", 1);
            // Append each character of the string to the string object
            for (int i = 0; i < stringToken.length(); i++) {
                vmWriter.writePush("constant", stringToken.charAt(i));
                vmWriter.writeCall("String.appendChar", 2);
            }
        }
        // Check if the current token is an identifier (variable or object)
        else if (jackTokenizer.isIdentifier()) {
            String token = jackTokenizer.identifier();
            jackTokenizer.advance();
            // Check if the identifier is an array, and handle accordingly
            if (jackTokenizer.isSymbol('[')) {
                processWithParentheses(this::compileExpression);
                // Push the base address of the array and calculate the array index
                vmWriter.writePush(symbolTable.kindOf(token), symbolTable.indexOf(token));
                vmWriter.writeArithmetic("+");
                vmWriter.writePop("pointer", 1);
                vmWriter.writePush("that", 0);
            }
            // Check if the identifier is a function call or method call
            else if (jackTokenizer.isSymbol('(') || jackTokenizer.isSymbol('.')) {
                jackTokenizer.goBack();
                processCallFunction();
            }
            // Otherwise, it's a variable reference
            else {
                vmWriter.writePush(symbolTable.kindOf(token), symbolTable.indexOf(token));
            }
        }
        // Check if the current token is a parenthesis, indicating an expression
        else if (jackTokenizer.isSymbol('(')) {
            processWithParentheses(this::compileExpression);
        }
        // Handle keyword constants like 'this', 'false', 'null', and 'true'
        else if (jackTokenizer.isKeywordConstant()) {
            String keyword = getCurrentToken(jackTokenizer::keyWord);
            switch (keyword) {
                case "this":
                    vmWriter.writePush("pointer", 0);
                    break;
                case "false":
                case "null":
                    vmWriter.writePush("constant", 0);
                    break;
                case "true":
                    vmWriter.writePush("constant", 1);
                    vmWriter.writeArithmetic("neg");
                    break;
            }
        }
        // Handle unary operators like '-' or '~'
        else if (jackTokenizer.isUnaryOp()) {
            String op = getCurrentToken(jackTokenizer::symbol).toString();
            compileTerm();
            if (op.equals("-")) {
                op = "neg"; // Convert '-' to 'neg' operation
            }
            vmWriter.writeArithmetic(op);
        }
    }

    // Compile an expression list (for function calls or parameter passing)
    public int compileExpressionList() throws IOException {
        // (a,b,c,...)
        int parenthesesCount = 0;
        // check if params exist
        if (jackTokenizer.isSymbol(')')) {
            return parenthesesCount;
        }
        do {
            compileExpression();
            parenthesesCount++;
        } while (jackTokenizer.isSymbol(',') && getCurrentToken(jackTokenizer::symbol) != null);
        return parenthesesCount;

    }

    // Process variable declarations and add them to the symbol table
    private void processVariable(Predicate<JackTokenizer> shouldProcess) {
        while (shouldProcess.test(jackTokenizer)) {
            String kind = getCurrentToken(jackTokenizer::keyWord);
            String type = processKeywordOrIdentifier();
            processSymbolTable(type, kind, false);
            jackTokenizer.advance(); // skip ;
        }
    }

    // Add parameters or local variables to the symbol table
    private void processSymbolTable(String type, String kind, boolean hasType) {
        LinkedHashMap<String, String> params = processParams(() -> getCurrentToken(jackTokenizer::identifier), hasType);
        for (String name : params.keySet()) {
            if (hasType) {
                symbolTable.define(name, params.get(name), kind);
            } else {
                symbolTable.define(name, type, kind);
            }
        }
    }

    // Process parameters in a function or method or variable
    private LinkedHashMap<String, String> processParams(Supplier<String> function, boolean hasType) {
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        // check if params exist
        if (jackTokenizer.isSymbol(')')) {
            return params;
        }
        do {
            if (hasType) {
                String type = processKeywordOrIdentifier();
                params.put(function.get(), type);
            } else {
                params.put(function.get(), "");
            }
        } while (jackTokenizer.isSymbol(',') && getCurrentToken(jackTokenizer::symbol) != null);
        return params;
    }

    // Handle function calls (method or function)
    private void processCallFunction() throws IOException {
        // class name or function name
        String firstPart = getCurrentToken(jackTokenizer::identifier);
        String functionName;
        final int[] paramsCount = {0};
        // Main.main
        if (jackTokenizer.isSymbol('.')) {
            // .
            String dotSymbol = String.valueOf(getCurrentToken(jackTokenizer::symbol));
            // function name main
            String secondPart = getCurrentToken(jackTokenizer::identifier);
            String type = symbolTable.typeOf(firstPart);
            // new object
            if (type.equals("none")) {
                functionName = firstPart + dotSymbol + secondPart;
            }
            // object is exist
            else {
                paramsCount[0]++;
                vmWriter.writePush(symbolTable.kindOf(firstPart), symbolTable.indexOf(firstPart));
                functionName = symbolTable.typeOf(firstPart) + dotSymbol + secondPart;
            }
            // ( a , b ,c )
            processWithParentheses(() -> {
                paramsCount[0] += compileExpressionList();
                vmWriter.writeCall(functionName, paramsCount[0]);
            });
        } else if (jackTokenizer.isSymbol('(')) {
            vmWriter.writePush("pointer", 0);
            processWithParentheses(() -> {
                paramsCount[0] += compileExpressionList() + 1;
                vmWriter.writeCall(className + "." + firstPart, paramsCount[0]);
            });

        }
    }

    // Process keyword or identifier token
    private String processKeywordOrIdentifier() {
        String token;
        if (jackTokenizer.isKeyword()) {
            token = getCurrentToken(jackTokenizer::keyWord);
        } else {
            token = getCurrentToken(jackTokenizer::identifier);
        }
        return token;
    }

    // Process a block of code enclosed in parentheses or braces
    private void processWithParentheses(RunnableWithException function) throws IOException {
        jackTokenizer.advance();
        function.run();
        jackTokenizer.advance();
    }

    // Get the current token using a supplier and then advance the tokenizer
    private <T> T getCurrentToken(Supplier<T> function) {
        T token = function.get();
        jackTokenizer.advance();
        return token;
    }

}
