/**
 * A-Instruction: @value
 * C-Instruction: dest=comp;jump
 * This parser handles the following:
 *   - Remove white space
 *   - Remove comments
 *   - Remove labels
 *   - Replace symbols with addresses
 *   - Convert instructions to binary
 *   - Remove empty lines
 *
 * @param {string} input - The raw assembly code as a string.
 * @returns {object} - An object with methods to parse the assembly instructions.
 */
function cleanCode(input) {
  return input
    .split("\n") // Split the input into individual lines
    .map((line) => line.replace(/\s+/g, "")) // Remove all white space characters from each line
    .filter((line) => line.length > 0 && !line.startsWith("//")) // Remove empty lines and lines that start with comments
    .join("\n"); // Rejoin the lines into a single string separated by newline characters
}

/**
 * Parser function to process assembly instructions.
 *
 * @param {string} input - The raw assembly code as a string.
 * @returns {object} - An object with methods to navigate and extract parts of the instructions.
 */
function parser(input) {
  const cleanInput = cleanCode(input); // Clean the input by removing white space, comments, labels, etc.
  const lines = cleanInput.split("\n"); // Split the cleaned input into an array of instruction lines
  let currentLine = 0; // Initialize the current line index
  let currentInstruction = ""; // Initialize the current instruction string
  let currentType = ""; // Initialize the current instruction type

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
    commandType: () => currentType,
    symbol: () => {
      if (currentType === "A_INSTRUCTION")
        return currentInstruction.substring(1);
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
    reset: () => {
      currentLine = 0;
      currentInstruction = "";
      currentType = "";
    },
  };
}
module.exports = { parser };
