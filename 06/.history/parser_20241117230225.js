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
  let currentInstruction = "";
  let currentType = "";

  const instructionType = () => {
    if (currentInstruction.startsWith("@")) return "A_INSTRUCTION";
    if (currentInstruction.startsWith("(")) return "L_INSTRUCTION";
    return "C_INSTRUCTION";
  };

  return {
    hasMoreLines: () => currentLine < lines.length,
    advance: () => {
      currentInstruction = lines[currentLine];
      currentType = instructionType();
      currentLine++;
    },
    commentType: () => currentType,
    symbol: () => {
      if (currentType === "A_COMMAND") return currentInstruction.substring(1);
      return currentInstruction.substring(1, currentInstruction.length - 1);
    },
    dest: () => {
      if (currentInstruction.includes("="))
        return currentInstruction.split("=")[0];
      return "null";
    },
    comp: () => {
      let comp = currentInstruction;
      if (currentInstruction.includes("="))
        comp = currentInstruction.split("=")[1];
      if (currentInstruction.includes(";"))
        comp = currentInstruction.split(";")[0];
      return comp;
    },
    jump: () => {
      if (currentInstruction.includes(";"))
        return currentInstruction.split(";")[1];
      return "null";
    },
  };
}
module.exports = { parser };
