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
    addEntry: (symbol, address)=> {
      TABLE[symbol] = address;
    },
    contains:  (symbol) {
      return TABLE.hasOwnProperty(symbol);
    },
    getAddress: function (symbol) {
      return TABLE[symbol];
    },
    getNextAvailableAddress: function () {
      return nextAddress++;
    },
  };
}
