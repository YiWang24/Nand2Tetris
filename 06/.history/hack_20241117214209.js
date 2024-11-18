const fs = require("fs");
const path = require("path");
const { createParser } = require("./parser");
const { dest, comp, jump, createSymbolTable } = require("./symbolTable");


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

  function secondPass() {}

  firstPass();
  return secondPass();
}

const input = readFile("test.asm");
const output = assemble(input);
console.log(output);
