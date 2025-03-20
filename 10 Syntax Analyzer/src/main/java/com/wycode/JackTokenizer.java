package com.wycode;

import java.io.*;
import java.util.*;
import java.util.function.Predicate;

/**
 * Provides routines that skip comments and white space, get the next token, and advance
 * the input exactly beyond it. Other routines return the type of the current token, and its value.
 *
 * @author WY
 * @version 1.0
 **/

public class JackTokenizer {

    private final List<String> tokens;
    private final List<String> tokenTypes;
    private int currentIndex = 0;
    private String currentToken;
    private String currentType;


    public JackTokenizer(File file) throws IOException {
        tokens = new ArrayList<>();
        tokenTypes = new ArrayList<>();
        readTokens(file);
        currentToken = tokens.get(currentIndex);
        currentType = tokenTypes.get(currentIndex);
    }

    public boolean hasMoreTokens() {
        return currentIndex < tokens.size();
    }

    public void advance() {
        if (hasMoreTokens()) {
            currentIndex++;
           if(currentIndex < tokens.size()) {
               currentToken = tokens.get(currentIndex);
               currentType = tokenTypes.get(currentIndex);
           }
        }
    }

    public void goBack() {
        if (currentIndex > 0) {
            currentIndex = currentIndex - 1;
            currentToken = tokens.get(currentIndex);
            currentType = tokenTypes.get(currentIndex);
        }
    }

    public String tokenType() {
        return currentType;
    }

    public String keyWord() {
        return currentToken;
    }

    public boolean isKeyword(String keyword) {
        return isKeyword() && currentToken.equals(keyword);
    }

    public boolean isKeyword() {
        return currentType.equals(TokenType.KEYWORD);
    }

    public char symbol() {
        return currentToken.charAt(0);
    }

    public boolean isSymbol(char c) {
        return currentType.equals(TokenType.SYMBOL) && tokenType().equals(TokenType.SYMBOL) && symbol() == c;
    }

    public boolean isSymbol() {
        return currentType.equals(TokenType.SYMBOL);
    }

    public String identifier() {
        return currentToken;
    }

    public boolean isIdentifier() {
        return currentType.equals(TokenType.IDENTIFIER);
    }

    public int intVal() {
        return Integer.parseInt(currentToken);
    }

    public boolean isIntVal() {
        return currentType.equals(TokenType.INTEGER_CONSTANT);
    }

    public String stringVal() {
        return currentToken;
    }

    public boolean isStringVal() {
        return currentType.equals(TokenType.STRING_CONSTANT);
    }

    public boolean isUnaryOp() {
        return isSymbol() && TokenType.UNARY_OP.contains(symbol());
    }

    public boolean isOp() {
        return isSymbol() && TokenType.OP.contains(symbol());
    }

    public boolean isKeywordConstant() {
        return isSymbol() && TokenType.KEYWORD_CONSTANT.contains(keyWord());
    }

    private void readTokens(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder sourceCode = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.replaceAll("//.*", "");
            if (!line.isEmpty()) {
                sourceCode.append(line);
            }
        }
        reader.close();
        String cleanedSourceCode = cleanComment(sourceCode.toString());
        tokenize(cleanedSourceCode);
    }

    private String cleanComment(String code) {
        code = code.replaceAll("/\\*.*?\\*/", "");
        return code.replaceAll("\\s+", " ").trim();
    }

    private void tokenize(String code) {
        while (!code.isEmpty()) {
            while (code.charAt(0) == ' ') {
                code = code.substring(1);
            }
            //Keyword
            for (String keyword : TokenType.KEYWORDS) {
                if (code.startsWith(keyword)) {
                    tokens.add(keyword);
                    tokenTypes.add(TokenType.KEYWORD);
                    code = code.substring(keyword.length());
                    break;
                }
            }
            char startChar = code.charAt(0);
            //Symbol
            if (TokenType.SYMBOLS.contains(startChar)) {
                tokens.add(String.valueOf(startChar));
                tokenTypes.add(TokenType.SYMBOL);
                code = code.substring(1);
            }
            //Integer constant
            else if (Character.isDigit(startChar)) {
                String token = extractToken(code, Character::isDigit);
                tokens.add(token);
                tokenTypes.add(TokenType.INTEGER_CONSTANT);
                code = code.substring(token.length());
            }
            //String constant
            else if (startChar == '"') {
                String token = String.valueOf(startChar);
                token += extractToken(code.substring(1), c -> c != '"');
                token += String.valueOf(startChar);
                tokens.add(token);
                tokenTypes.add(TokenType.STRING_CONSTANT);
                code = code.substring(token.length());
            }
            //Identifier
            else if (Character.isLetter(startChar) || startChar == '_') {
                String token = extractToken(code, c -> Character.isLetter(c) || c == '_');
                tokens.add(token);
                tokenTypes.add(TokenType.IDENTIFIER);
                code = code.substring(token.length());
            }
        }

    }

    private String extractToken(String code, Predicate<Character> condition) {
        if (code.isEmpty() || !condition.test(code.charAt(0))) {
            return "";
        }
        return code.charAt(0) + extractToken(code.substring(1), condition);
    }


}
