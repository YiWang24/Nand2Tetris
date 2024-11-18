import { COMP, DEST, JUMP, TABLE } from "./constants";

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

function createParser(input){
    const lines = input.split('\n');
    let currentLine = 0;
    let currentCommand = '';
    return {
        
        hasMoreCommands: () => currentLine < lines.length,
        advance: () => {
            currentCommand = lines[currentLine].trim();
            currentLine++;
        },
        commandType: () => {
            if(currentCommand.startsWith('@')) return 'A_COMMAND';
            if(currentCommand.startsWith('(')) return 'L_COMMAND';
            return 'C_COMMAND';
        },
        symbol: () => {
            if(this.commandType() === 'A_COMMAND') return currentCommand.substring(1);
            return currentCommand.substring(1, currentCommand.length - 1);
        },
        dest: () => {
            if(currentCommand.includes('=')) return currentCommand.split('=')[0];
            return 'null';
        },
        comp: () => {
            let comp = currentCommand;
            if(currentCommand.includes('=')) comp = currentCommand.split('=')[1];
            if(currentCommand.includes(';')) comp = currentCommand.split(';')[0];
            return comp;
        },
        jump: () => {
            if(currentCommand.includes(';')) return currentCommand.split(';')[1];
            return 'null';
        }
    }
}

function assemble(input){
    const parser = createParser(input);
    const symbolTable = createSymbolTable();

    function 
}