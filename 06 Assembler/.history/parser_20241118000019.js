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

  /**
   * Determines the type of the current instruction.
   *
   * @returns {string} - The type of the instruction: "A_INSTRUCTION", "L_INSTRUCTION", or "C_INSTRUCTION".
   */
  const instructionType = () => {
    if (currentInstruction.startsWith("@")) return "A_INSTRUCTION"; // A-Instruction (e.g., @2, @LOOP)
    if (currentInstruction.startsWith("(")) return "L_INSTRUCTION"; // Label Declaration (e.g., (LOOP))
    return "C_INSTRUCTION"; // C-Instruction (e.g., D=A, 0;JMP)
  };

  return {
    /**
     * Checks if there are more instructions to process.
     *
     * @returns {boolean} - True if there are more lines to process, otherwise false.
     */
    hasMoreLines: () => currentLine < lines.length,

    /**
     * Advances to the next instruction.
     * Updates the current instruction and its type.
     */
    advance: () => {
      currentInstruction = lines[currentLine]; // Set the current instruction to the line at currentLine index
      currentType = instructionType(); // Determine the type of the current instruction
      currentLine++; // Move to the next line
    },

    /**
     * Retrieves the type of the current instruction.
     *
     * @returns {string} - The type of the instruction: "A_INSTRUCTION", "L_INSTRUCTION", or "C_INSTRUCTION".
     */
    commandType: () => currentType,

    /**
     * Extracts the symbol or decimal value from an A-Instruction or Label Declaration.
     *
     * @returns {string} - The symbol name or numeric value.
     */
    symbol: () => {
      if (currentType === "A_INSTRUCTION")
        return currentInstruction.substring(1); // Return everything after "@" in A-Instruction
      return currentInstruction.substring(1, currentInstruction.length - 1); // Return the label name without parentheses
    },

    /**
     * Extracts the destination mnemonic from a C-Instruction.
     *
     * @returns {string} - The destination mnemonic (e.g., "D", "M", "MD") or "null" if not present.
     */
    dest: () => {
      if (currentInstruction.includes("="))
        return currentInstruction.split("=")[0]; // Split at "=" and return the left part as dest
      return "null"; // Return "null" if there's no dest part
    },

    /**
     * Extracts the computation mnemonic from a C-Instruction.
     *
     * @returns {string} - The computation mnemonic (e.g., "D+1", "A-1", "D&A").
     */
    comp: () => {
      let comp = currentInstruction;
      if (currentInstruction.includes("="))
        comp = currentInstruction.split("=")[1]; // If there's a dest part, get the computation part after "="
      if (currentInstruction.includes(";"))
        comp = currentInstruction.split(";")[0]; // If there's a jump part, get the computation part before ";"
      return comp; // Return the computation mnemonic
    },

    /**
     * Extracts the jump mnemonic from a C-Instruction.
     *
     * @returns {string} - The jump mnemonic (e.g., "JGT", "JEQ") or "null" if not present.
     */
    jump: () => {
      if (currentInstruction.includes(";"))
        return currentInstruction.split(";")[1]; // Split at ";" and return the right part as jump
      return "null"; // Return "null" if there's no jump part
    },
  };
}

// Export the parser function for use in other modules
module.exports = { parser };
