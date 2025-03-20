const fs = require("fs");
const path = require("path");
const { COMP, DEST, JUMP, TABLE } = require("./symbolTable");

function dest(mnemonic) {
  return DEST[mnemonic];
}
function comp(mnemonic) {
  return COMP[mnemonic];
}
function jump(mnemonic) {
  return JUMP[mnemonic];
}

function createSymbolTable() {
  let nextAddress = 16;
  return {
    addEntry: (symbol, address) => (TABLE[symbol] = address),
    contains: (symbol) => TABLE.hasOwnProperty(symbol),
    getAddress: (symbol) => TABLE[symbol],
    getNextAvailableAddress: () => nextAddress++,
  };
}

function readFile(filePath) {
  return fs.readFileSync(path.join(__dirname, filePath), "utf8");
}

function cleanCode(input) {
  return input
    .split("\n")
    .map((line) => line.replace(/\s+/g, ""))
    .filter((line) => line.length > 0 && !line.startsWith("//"))
    .join("\n");
}

/**
 * A-Instruction: @value
 * C-Instruction: dest=comp;jump
 * handle white space   // remove white space
 * handle comments      // remove comments
 * handle labels        // remove labels
 * handle symbols       // replace symbols with addresses
 * handle instructions  // convert instructions to binary
 * handle empty lines   // remove empty lines
 *
 * @param {*} input
 * @returns
 */

function assemble(input) {
  const cleanInput = cleanCode(input);
  const parser = createParser(cleanInput);
  const symbolTable = createSymbolTable();

  function firstPass() {
    let romAddress = 0;
    while (parser.hasMoreCommands()) {
      parser.getCommand();
      if (parser.commandType() === "L_COMMAND") {
        symbolTable.addEntry(parser.symbol(), romAddress);
      } else {
        romAddress++;
      }
    }
  }

  function secondPass() {
   
    
  }

  firstPass();
  return secondPass();
}

const input = readFile("test.asm");
const output = assemble(input);
console.log(output);
