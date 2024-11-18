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

  function secondPass() {
    let output = "";
    parser.reset();
    while (parser.hasMoreLines()) {
      parser.advance();

      switch (parser.commandType()) {
        case "A_INSTRUCTION":
          let symbol = parser.symbol();
          if (!isNaN(symbol)) {
            address = parseInt(symbol);
            output += `0${address.toString(2).padStart(15, "0")}\n`;
          } else {
            if (symbolTable.contains(symbol)) {
              output += `0${symbolTable
                .getAddress(symbol)
                .toString(2)
                .padStart(15, "0")}\n`;
            } else {
              symbolTable.addEntry(symbol, symbolTable.nextAvailableAddress());
              symbolTable.getNextAvailableAddress();
              output += `0${symbolTable
                .getAddress(symbol)
                .toString(2)
                .padStart(15, "0")}\n`;
            }
          }

          break;
        case "C_INSTRUCTION":
          output += `111${comp(parser.comp())}${dest(parser.dest())}${jump(
            parser.jump()
          )}\n`;
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
