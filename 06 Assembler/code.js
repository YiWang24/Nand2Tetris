const fs = require("fs");
const path = require("path");
const { parser: createParser } = require("./parser");
const { dest, comp, jump, createSymbolTable } = require("./symbolTable");

/**
 * Reads the content of a file.
 *
 * @param {string} filePath - The path to the file to be read.
 * @returns {string} - The content of the file as a string.
 */
function readFile(filePath) {
  return fs.readFileSync(path.join(__dirname, filePath), "utf8");
}

/**
 * Writes data to a file.
 *
 * @param {string} filePath - The path to the file where data will be written.
 * @param {string} data - The data to write to the file.
 */
function writeFile(filePath, data) {
  fs.writeFileSync(path.join(__dirname, filePath), data, "utf8");
}

/**
 * Assembles the input assembly code into machine code.
 *
 * @param {string} input - The raw assembly code as a string.
 * @returns {string} - The assembled machine code as a string.
 */
function assemble(input) {
  const parser = createParser(input); // Create a parser instance with the input code
  const symbolTable = createSymbolTable(); // Create a new symbol table

  /**
   * First pass: Process all label declarations and add them to the symbol table.
   */
  function firstPass() {
    let romAddress = 0; // Initialize ROM address counter to 0

    // Loop through all instructions
    while (parser.hasMoreLines()) {
      parser.advance(); // Move to the next instruction

      if (parser.commandType() === "L_INSTRUCTION") {
        // Check if it's a label declaration
        symbolTable.addEntry(parser.symbol(), romAddress); // Add label and its address to the symbol table
      } else {
        romAddress++; // Increment ROM address for actual instructions
      }
    }
  }

  /**
   * Second pass: Convert all instructions to machine code, handling symbols.
   *
   * @returns {string} - The final machine code output.
   */
  function secondPass() {
    let output = ""; // Initialize the output string
    parser.reset(); // Reset the parser to the beginning

    // Loop through all instructions again
    while (parser.hasMoreLines()) {
      parser.advance(); // Move to the next instruction

      switch (
        parser.commandType() // Determine the type of instruction
      ) {
        case "A_INSTRUCTION": // Handle A-instructions (e.g., @2, @LOOP)
          let symbol = parser.symbol(); // Get the symbol or number from the A-instruction
          let address; // Variable to store the address

          if (!isNaN(symbol)) {
            // Check if the symbol is a number
            address = parseInt(symbol); // Convert the string number to an integer
            output += `0${address.toString(2).padStart(15, "0")}\n`; // Convert to 16-bit binary and add to output
          } else {
            if (symbolTable.contains(symbol)) {
              // Check if the symbol is already in the symbol table
              address = symbolTable.getAddress(symbol); // Get the address from the symbol table
            } else {
              address = symbolTable.getNextAvailableAddress(); // Get the next available address for a new variable
              symbolTable.addEntry(symbol, address); // Add the new variable to the symbol table
            }
            output += `0${address.toString(2).padStart(15, "0")}\n`; // Convert to 16-bit binary and add to output
          }

          break;
        case "C_INSTRUCTION": // Handle C-instructions (e.g., D=A, 0;JMP)
          const destCode = dest(parser.dest()); // Get the binary code for the dest part
          const compCode = comp(parser.comp()); // Get the binary code for the comp part
          const jumpCode = jump(parser.jump()); // Get the binary code for the jump part
          output += `111${compCode}${destCode}${jumpCode}\n`; // Combine parts into a 16-bit binary instruction
          break;
      }
    }

    return output;
  }

  firstPass();
  return secondPass();
}

const input = readFile("test.asm");
const output = assemble(input);

writeFile("test.hack", output);
