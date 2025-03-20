package com.wycode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/**
 * Gets its input from a JackTokenizer and emits its output to an output file, using a
 * set of parsing routines. Each compile routine is responsible for handling all the tokens that make up
 * xxx, advancing the tokenizer exactly beyond these tokens, and outputting the parsing of xxx.
 *
 * @author WY
 * @version 1.0
 **/
@FunctionalInterface
interface RunnableWithException {
    void run() throws IOException;
}

public class CompilationEngine {
    private final JackTokenizer jackTokenizer;
    private final FileWriter fileWriter;

    public CompilationEngine(File inputFile, File outputFile) throws IOException {
        jackTokenizer = new JackTokenizer(inputFile);
        fileWriter = new FileWriter(outputFile);
    }

    public void compileClass() throws IOException {
        compileWrapper("class", () -> {
            // class
            writeCurrentTokens(jackTokenizer.keyWord());
            // className
            writeCurrentTokens(jackTokenizer.identifier());
            // {
            writeCurrentTokens(jackTokenizer.symbol());
            // variable declaration
            compileClassVarDec();
            // methods declaration
            compileSubroutine();
            // }
            writeCurrentTokens(jackTokenizer.symbol());
        });
        fileWriter.close();
    }

    public void compileClassVarDec() throws IOException {
        while (jackTokenizer.isKeyword("static") || jackTokenizer.isKeyword("field")) {
            compileWrapper("classVarDec", this::processVariables);
        }
    }

    public void compileSubroutine() throws IOException {
        while ((jackTokenizer.isKeyword("constructor") ||
                jackTokenizer.isKeyword("function") ||
                jackTokenizer.isKeyword("method"))) {
            compileWrapper("subroutineDec", () -> {
                // constructor function method
                writeCurrentTokens(jackTokenizer.keyWord());
                // keyword type or identifier
                processKeywordOrIdentifier();
                // function name
                writeCurrentTokens(jackTokenizer.identifier());
                // parameters
                processWithParentheses(this::compileParameterList);
                // subroutine body
                compileSubroutineBody();
            });
        }
    }

    public void compileParameterList() throws IOException {
        compileWrapper("parameterList", () ->
                processParams(() -> writeCurrentTokens(jackTokenizer.identifier()), true)
        );
    }

    public void compileSubroutineBody() throws IOException {
        compileWrapper("subroutineBody", () -> processWithParentheses(() -> {
            compileVarDec();
            compileStatements();
        }));
    }

    public void compileVarDec() throws IOException {
        while (jackTokenizer.isKeyword("var")) {
            compileWrapper("varDec", this::processVariables);
        }
    }

    public void compileStatements() throws IOException {
        compileWrapper("statements", () -> {
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
        });
    }

    public void compileLet() throws IOException {
        compileWrapper("letStatement", () -> {
            // let
            writeCurrentTokens(jackTokenizer.keyWord());
            // varName
            writeCurrentTokens(jackTokenizer.identifier());
            // [express]
            if (jackTokenizer.isSymbol('[')) {
                // [ expression ]
                processWithParentheses(this::compileExpression);
            }
            // =
            writeCurrentTokens(jackTokenizer.symbol());
            // express;
            compileExpression();
            // ;
            writeCurrentTokens(jackTokenizer.symbol());
        });
    }

    public void compileIf() throws IOException {
        compileWrapper("ifStatement", () -> {
            // if
            writeCurrentTokens(jackTokenizer.keyWord());
            // (expression)
            processWithParentheses(this::compileExpression);
            // { statement }
            processWithParentheses(this::compileStatements);
            if (jackTokenizer.isKeyword("else")) {
                // else
                writeCurrentTokens(jackTokenizer.keyWord());
                // {statement}
                processWithParentheses(this::compileStatements);
            }
        });
    }

    public void compileWhile() throws IOException {
        compileWrapper("whileStatement", () -> {
            // While
            writeCurrentTokens(jackTokenizer.keyWord());
            // ( expression )
            processWithParentheses(this::compileExpression);
            //{ statements}
            processWithParentheses(this::compileStatements);
        });
    }

    public void compileDo() throws IOException {
        compileWrapper("doStatement", () -> {
            // Do
            writeCurrentTokens(jackTokenizer.keyWord());
            // handle function
            processCallFunction();
            //;
            writeCurrentTokens(jackTokenizer.symbol());
        });
    }

    public void compileReturn() throws IOException {
        compileWrapper("returnStatement", () -> {
            // return
            writeCurrentTokens(jackTokenizer.keyWord());
            if (!jackTokenizer.isSymbol(';')) {
                compileExpression();
            }
            writeCurrentTokens(jackTokenizer.symbol());
        });
    }

    public void compileExpression() throws IOException {
        compileWrapper("expression", () -> {
            compileTerm();
            while (jackTokenizer.isOp()) {
                writeCurrentTokens(jackTokenizer.symbol());
                compileTerm();
            }
        });
    }

    public void compileTerm() throws IOException {
        compileWrapper("term", () -> {
            if (jackTokenizer.isIntVal()) {
                writeCurrentTokens(jackTokenizer.intVal());
            } else if (jackTokenizer.isStringVal()) {
                writeCurrentTokens(jackTokenizer.stringVal());
            } else if (jackTokenizer.isKeyword()) {
                writeCurrentTokens(jackTokenizer.keyWord());
            } else if (jackTokenizer.isIdentifier()) {
                String token = jackTokenizer.identifier();
                jackTokenizer.advance();
                if (jackTokenizer.isSymbol('[')) {
                    jackTokenizer.goBack();
                    writeCurrentTokens(token);
                    processWithParentheses(this::compileExpression);
                } else if (jackTokenizer.isSymbol('(') || jackTokenizer.isSymbol('.')) {
                    jackTokenizer.goBack();
                    processCallFunction();
                } else {
                    jackTokenizer.goBack();
                    writeCurrentTokens(token);
                }
            } else if (jackTokenizer.isSymbol('(')) {
                processWithParentheses(this::compileExpression);
            } else if (jackTokenizer.isKeywordConstant()) {
                writeCurrentTokens(jackTokenizer.keyWord());
            } else if (jackTokenizer.isUnaryOp()) {
                writeCurrentTokens(jackTokenizer.symbol());
                compileTerm();
            }
        });
    }

    public int compileExpressionList() throws IOException {
        fileWriter.write("<expressionList>\n");
        // (a,b,c,...)
        int expressionCount = processParams(this::compileExpression, false);
        fileWriter.write("</expressionList>\n");
        return expressionCount;
    }

    private void compileWrapper(String tagName, RunnableWithException content) throws IOException {
        fileWriter.write("<" + tagName + ">\n");
        content.run();
        fileWriter.write("</" + tagName + ">\n");
    }

    private void processCallFunction() throws IOException {
        // class name or function name
        writeCurrentTokens(jackTokenizer.identifier());
        // Main.main
        if (jackTokenizer.isSymbol('.')) {
            // .
            writeCurrentTokens(jackTokenizer.symbol());
            // function name main
            writeCurrentTokens(jackTokenizer.identifier());
        }
        // ( a , b ,c )
        processWithParentheses(this::compileExpressionList);

    }

    private void processVariables() throws IOException {
        // keyword static or field
        writeCurrentTokens(jackTokenizer.keyWord());
        // class name or keyword
        processKeywordOrIdentifier();
        // a,b,c
        processParams(() -> writeCurrentTokens(jackTokenizer.identifier()), false);
        //;
        writeCurrentTokens(jackTokenizer.symbol());
    }

    // write identifier or keyword
    private void processKeywordOrIdentifier() throws IOException {
        if (jackTokenizer.isKeyword()) {
            writeCurrentTokens(jackTokenizer.keyWord());
        } else {
            writeCurrentTokens(jackTokenizer.identifier());
        }
    }

    // deal with params like (a,b,c)  or  (int o, int b, int c)
    private int processParams(RunnableWithException function, boolean hasType) throws IOException {
        int expressionCount = 0;
        boolean hasComma;
        // check if params exist
        if (jackTokenizer.isSymbol(')')) {
            return expressionCount;
        }
        do {
            expressionCount = expressionCount + 1;
            // deal with type of params
            if (hasType) {
                processKeywordOrIdentifier();
            }
            // write params or expression
            function.run();
            // check if there are more comma
            hasComma = jackTokenizer.isSymbol(',');
            // write comma ,
            if (hasComma) {
                writeCurrentTokens(jackTokenizer.symbol());
            }
        } while (hasComma);
        return expressionCount;
    }

    // ( expression ) or { statements }
    private void processWithParentheses(RunnableWithException function) throws IOException {
        // (
        writeCurrentTokens(jackTokenizer.symbol());
        // expression
        function.run();
        // )
        writeCurrentTokens(jackTokenizer.symbol());
    }

    private void writeCurrentTokens(String token) throws IOException {
        String type = jackTokenizer.tokenType();

        fileWriter.write(String.format("<%s> %s </%s>\n", type, token.replaceAll("\"", ""), type));
        jackTokenizer.advance();
    }

    private void writeCurrentTokens(char token) throws IOException {
        String type = jackTokenizer.tokenType();
        String value;
        switch (token) {
            case '<':
                value = "&lt;";
                break;
            case '>':
                value = "&gt;";
                break;
            case '&':
                value = "&amp;";
                break;
            case '"':
                value = "&quot;";
                break;
            default:
                value = String.valueOf(token);
        }
        fileWriter.write(String.format("<%s> %s </%s>\n", type, value, type));
        jackTokenizer.advance();
    }

    private void writeCurrentTokens(int token) throws IOException {
        String type = jackTokenizer.tokenType();
        fileWriter.write(String.format("<%s> %s </%s>\n", type, token, type));
        jackTokenizer.advance();
    }
}
