const compMap = {
    '0': '0101010',
    '1': '0111111',
    '-1': '0111010',
    'D': '0001100',
    'A': '0110000',
    'M': '1110000',
    '!D': '0001101',
    '!A': '!M': '0110001',
    '-D': '0001111',
    '-A': '-M': '0110011',
    'D+1': '0011111',
    'A+1': 'M+1': '0110111',
    'D-1': '0001110',
    'A-1': 'M-1': '0110010',
    'D+A': 'D+M': '0000010',
    'D-A': 'D-M': '0010011',
    'A-D': 'M-D': '0000111',
    'D&A': 'D&M': '0000000',
    'D|A': 'D|M': '0010101'
  };
  
  // Example usage:
  console.log(compMap['D']); // Output: '0001100'