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
function cleanCode(input) {
  return input
    .split("\n")
    .map((line) => line.replace(/\s+/g, ""))
    .filter((line) => line.length > 0 && !line.startsWith("//"))
    .join("\n");
}

function parser(input) {
  const cleanInput = cleanCode(input);
  const lines = cleanInput.split("\n");
  let currentLine = 0;
  let currentCommand = "";
  let currentType = "";

  const getCommandType = () => {
    if (currentCommand.startsWith("@")) return "A_COMMAND";
    if (currentCommand.startsWith("(")) return "L_COMMAND";
    return "C_COMMAND";
  };

  return {
    hasMoreCommands: () => currentLine < lines.length,
    getCommand: () => {
      currentCommand = lines[currentLine];
      currentType = getCommandType();
      currentLine++;
    },
    symbol: () => {
      if (commandType() === "A_COMMAND")
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
module.exports = { parser };
