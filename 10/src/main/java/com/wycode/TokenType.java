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
    public static final Set<Character> UNARY_OP = new HashSet<>(Arrays.asList(
            '-', '~'));
    public static final Set<String> KEYWORD_CONSTANT = new HashSet<>(Arrays.asList(
            "true", "false", "null", "this"
    ));
    public static final Set<Character> OP = new HashSet<>(Arrays.asList(
            '+', '-', '*', '/', '&', '|', '<', '>', '='
    ));
    public static final String KEYWORD = "keyword";
    public static final String SYMBOL = "symbol";
    public static final String INTEGER_CONSTANT = "integerConstant";
    public static final String STRING_CONSTANT = "stringConstant";
    public static final String IDENTIFIER = "identifier";

}
