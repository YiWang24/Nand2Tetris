import { COMP, DEST, JUMP ,TABLE } from "./constants";

function dest(mnemonic) {
  return DEST[mnemonic];
}
