const fs = require("fs");
const path = require("path");
const { parser: createParser } = require("./parser");
const { dest, comp, jump, createSymbolTable } = require("./symbolTable");

function readFile(filePath) {
  return fs.readFileSync(path.join(__dirname, filePath), "utf8");
}

function writeFile(filePath, data) {
  fs.writeFileSync(path.join(__dirname, filePath), data, "utf8");
}

function assemble(input) {
  const parser = createParser(input);
  const symbolTable = createSymbolTable();

  function firstPass() {
    let romAddress = 16;
    while (parser.hasMoreCommands()) {
      parser.getCommand();
      if (parser.commandType() === "L_COMMAND") {
        symbolTable.addEntry(parser.symbol(), romAddress);
        romAddress++;
        console.log(symbolTable.getSymbolTable());
      }
    }
  }

  function secondPass() {
    let output = "";
    while (parser.hasMoreCommands()) {
      parser.getCommand();

      switch (parser.commandType()) {
        case "A_COMMAND":
          let symbol = parser.symbol();
          if (typeof symbol === "number") {
            output += `0${symbol.toBi
          } else if (!symbolTable.contains(symbol)) {
            symbolTable.addEntry(
              parser.symbol(),
              symbolTable.getSymbolTable().length
            );
          }
          
          break;
        case "C_COMMAND":
          output += `111${comp(parser.comp())}${dest(parser.dest())}${jump(
            parser.jump()
          )}\n`;
          break;
      }
    }
  }

  firstPass();
  return secondPass();
}

const input = readFile("test.asm");
const output = assemble(input);

// writeFile("test.hack", output);
