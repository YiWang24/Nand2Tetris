// Computation field mapping for Hack assembly
// Format: a c1 c2 c3 c4 c5 c6
// a=0 for A register, a=1 for M register

const COMP = {
    // Constants
    '0':   '0101010',
    '1':   '0111111',
    '-1':  '0111010',
  
    // Direct reference
    'D':   '0001100',
    'A':   '0110000',
    'M':   '1110000',
  
    // Logical NOT
    '!D':  '0001101',
    '!A':  '0110001',
    '!M':  '1110001',
  
    // Negation
    '-D':  '0001111',
    '-A':  '0110011',
    '-M':  '1110011',
  
    // Increment
    'D+1': '0011111',
    'A+1': '0110111',
    'M+1': '1110111',
  
    // Decrement
    'D-1': '0001110',
    'A-1': '0110010',
    'M-1': '1110010',
  
    // Binary operations
    'D+A': '0000010',
    'D+M': '1000010',
    'D-A': '0010011',
    'D-M': '1010011',
    'A-D': '0000111',
    'M-D': '1000111',
    'D&A': '0000000',
    'D&M': '1000000',
    'D|A': '0010101',
    'D|M': '1010101'
  };
  

// Destination field mapping (3 bits: d1 d2 d3)
// d1 = store in A register
// d2 = store in D register
// d3 = store in M register
const DEST = {
  'null': '000',  // No store
  'M':    '001',  // Memory
  'D':    '010',  // Data register
  'MD':   '011',  // Memory and Data register
  'A':    '100',  // Address register
  'AM':   '101',  // Address and Memory
  'AD':   '110',  // Address and Data register
  'AMD':  '111'   // All registers
};

// Jump field mapping (3 bits: j1 j2 j3)
// j1 = less than zero
// j2 = equal to zero 
// j3 = greater than zero
const JUMP = {
  'null': '000', // No jump
  'JGT':  '001', // Jump if greater than zero
  'JEQ':  '010', // Jump if equal to zero
  'JGE':  '011', // Jump if greater or equal
  'JLT':  '100', // Jump if less than zero
  'JNE':  '101', // Jump if not equal
  'JLE':  '110', // Jump if less or equal
  'JMP':  '111'  // Jump unconditionally
};

export { DEST, JUMP };


export { COMP };

