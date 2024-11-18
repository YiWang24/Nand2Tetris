
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
  