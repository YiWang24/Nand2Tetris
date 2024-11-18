const fs = require("fs");
const path = require("path");
const { COMP, DEST, JUMP, TABLE } = require("./constants");


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

function createParser(input) {
  const lines = input.split("\n");
  let currentLine = 0;
  let currentCommand = "";
  return {
    hasMoreCommands: () => currentLine < lines.length,
    getCommand: () => {
      currentCommand = lines[currentLine];
      currentLine++;
    },
    commandType: () => {
      if (currentCommand.startsWith("@")) return "A_COMMAND";
      if (currentCommand.startsWith("(")) return "L_COMMAND";
      return "C_COMMAND";
    },
    symbol: () => {
      if (this.commandType() === "A_COMMAND")
        return currentCommand.substring(1);
      return currentCommand.substring(1, currentCommand.length - 1);
    },
    dest: () => {
      if (currentCommand.includes("=")) return currentCommand.split("=")[0];
      return "null";
    },
    comp: () => {
      let comp = currentCommand;
      if (currentCommand.includes("=")) comp = currentCommand.split("=")[1];
      if (currentCommand.includes(";")) comp = currentCommand.split(";")[0];
      return comp;
    },
    jump: () => {
      if (currentCommand.includes(";")) return currentCommand.split(";")[1];
      return "null";
    },
  };
}

function assemble(input) {
  const cleanInput = cleanCode(input);
  const parser = createParser(cleanInput);
  const symbolTable = createSymbolTable();
  let output = "";
  while (parser.hasMoreCommands()) {
    parser.getCommand();
    switch (parser.commandType()) {
      case "A_COMMAND":
        const symbol = parser.symbol();
        if (isNaN(symbol)) {
          if (!symbolTable.contains(symbol)) {
            symbolTable.addEntry(symbol, symbolTable.getNextAvailableAddress());
          }
          symbolTable.getAddress(symbol);
        }
        break;
      case "C_COMMAND":
        const destMnemonic = parser.dest();
        const compMnemonic = parser.comp();
        const jumpMnemonic = parser.jump();
        output +=
          "111" +
          comp(compMnemonic) +
          dest(destMnemonic) +
          jump(jumpMnemonic) +
          "\n";
        break;
        case "L_COMMAND":
        const symbol = parser.symbol();
    }
  }
  return output;
}

const input = readFile("test.asm");
const output = assemble(input);
console.log(output);
