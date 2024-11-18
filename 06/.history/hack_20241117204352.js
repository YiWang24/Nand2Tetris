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
  return {
    addEntry: function(symbol, address) {
      TABLE[symbol] = address;
    },
    contains: function(symbol) {
      return TABLE.hasOwnProperty(symbol);
    },
    getAddress: function(symbol) {
      return TABLE[symbol];
    },
    get
  }
}
