package com.wycode;

import java.util.HashMap;
import java.util.function.Function;

/**
 * The `SymbolTable` class represents a symbol table for the Jack programming language.
 * It stores class-level (static and field) and method-level (argument and local) variables.
 * The table keeps track of variable names, types, kinds, and their corresponding indices.
 *
 * This class is essential for handling variable declarations, scoping, and type checking
 * in a Jack compiler.
 *
 * @author WY
 * @version 1.0
 */
public class SymbolTable {
    private final HashMap<String, Symbol> classTable;  // Stores class-level symbols (static, field)
    private final HashMap<String, Symbol> methodTable; // Stores method-level symbols (argument, var)
    private final HashMap<String, Integer> index;      // Keeps track of the index for each kind

    /**
     * The `Symbol` class represents an individual symbol (variable).
     * Each symbol has a type, a kind (scope), and a unique index number.
     */
    private class Symbol {
        private String type;   // Data type of the variable (e.g., int, boolean, custom class)
        private String kind;   // Kind of the variable (static, field, argument, var)
        private int number;    // Index of the variable in its category

        /**
         * Constructs a new Symbol.
         *
         * @param type The data type of the symbol.
         * @param kind The kind of the symbol (static, field, argument, var).
         * @param number The unique index of the symbol in its category.
         */
        public Symbol(String type, String kind, int number) {
            this.type = type;
            this.kind = kind;
            this.number = number;
        }
    }

    /**
     * Initializes a new symbol table.
     * The class-level and method-level symbol tables are separate.
     * The index map tracks the count of each kind (static, field, argument, var).
     */
    public SymbolTable() {
        classTable = new HashMap<>();
        methodTable = new HashMap<>();
        index = new HashMap<>();
        index.put("static", 0);
        index.put("field", 0);
        index.put("argument", 0);
        index.put("var", 0);
    }

    /**
     * Resets the method-level symbol table when a new method is being processed.
     * This clears all "argument" and "var" variables but retains class-level symbols.
     */
    public void reset() {
        methodTable.clear();
        index.put("argument", 0);
        index.put("var", 0);
    }

    /**
     * Defines a new variable in the symbol table.
     * The variable is added to the appropriate table (classTable or methodTable).
     *
     * @param name The name of the variable.
     * @param type The data type of the variable.
     * @param kind The kind of the variable (static, field, argument, var).
     */
    public void define(String name, String type, String kind) {
        Symbol symbol = new Symbol(type, kind, index.get(kind));
        index.put(kind, index.get(kind) + 1);

        if (kind.equals("argument") || kind.equals("var")) {
            methodTable.put(name, symbol);
        } else if (kind.equals("static") || kind.equals("field")) {
            classTable.put(name, symbol);
        }
    }

    /**
     * Returns the number of variables of a given kind defined in the symbol table.
     *
     * @param kind The kind of variable (static, field, argument, var).
     * @return The count of variables of the specified kind.
     */
    public int varCount(String kind) {
        return index.get(kind);
    }

    /**
     * Returns the kind of a variable (static, field, argument, var).
     * If the variable is not found, returns "none".
     *
     * @param name The name of the variable.
     * @return The kind of the variable, or "none" if it is not found.
     */
    public String kindOf(String name) {
        return lookup(name, symbol -> symbol.kind, "none");
    }

    /**
     * Returns the type of a variable.
     * If the variable is not found, returns "none".
     *
     * @param name The name of the variable.
     * @return The type of the variable, or "none" if it is not found.
     */
    public String typeOf(String name) {
        return lookup(name, symbol -> symbol.type, "none");
    }

    /**
     * Returns the index of a variable in its kind category.
     * If the variable is not found, returns -1.
     *
     * @param name The name of the variable.
     * @return The index of the variable, or -1 if it is not found.
     */
    public int indexOf(String name) {
        return lookup(name, symbol -> symbol.number, -1);
    }

    /**
     * A generic lookup method to search for a symbol in both method and class tables.
     *
     * @param <T> The return type of the function.
     * @param name The name of the symbol to look up.
     * @param getter A function that extracts a property from the Symbol object.
     * @param defaultValue The default value to return if the symbol is not found.
     * @return The extracted property or the default value.
     */
    private <T> T lookup(String name, Function<Symbol, T> getter, T defaultValue) {
        if (methodTable.containsKey(name)) {
            return getter.apply(methodTable.get(name));
        } else if (classTable.containsKey(name)) {
            return getter.apply(classTable.get(name));
        } else {
            return defaultValue;
        }
    }
}
