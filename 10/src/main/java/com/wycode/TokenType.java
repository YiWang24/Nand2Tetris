package com.wycode;

import java.util.*;

/**
 * @author WY
 * @version 1.0
 **/

public class TokenType {
    public static final Set<String> KEYWORDS = new HashSet<>(Arrays.asList(
            "class", "constructor", "function", "method", "field", "static",
            "var", "int", "char", "boolean", "void", "true", "false", "null",
            "this", "let", "do", "if", "else", "while", "return"
    ));
    public static final Set<Character> SYMBOLS = new HashSet<>(Arrays.asList(
            '{', '}', '(', ')', '[', ']', '.', ',', ';', '+', '-', '*', '/',
            '&', '|', '<', '>', '=', '~'
    ));
    public static int INTEGER_LOW_BOUND = 0;
    public static int INTEGER_HIGH_BOUND = 32767;
    public static final String KEYWORD = "keyword";
    public static final String SYMBOL = "symbol";
    public static final String INTEGER_CONSTANT = "integerConstant";
    public static final String STRING_CONSTANT = "stringConstant";
    public static final String IDENTIFIER = "identifier";

}
