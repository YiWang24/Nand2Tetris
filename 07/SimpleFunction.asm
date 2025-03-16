

// FUNCTION SimpleFunction.vm$SIMPLEFUNCTION.TEST 2
// Function's entry point
(SimpleFunction.vm$SIMPLEFUNCTION.TEST)
//push nVars 0 values (initializes the callee’s local variables)
@2
D=A
(SimpleFunction.vm$SIMPLEFUNCTION.TEST$LOOP_2)
@SimpleFunction.vm$SIMPLEFUNCTION.TEST$END_2
D;JEQ
@0
D=A
@SP
A=M
M=D
@SP
M=M+1
D=D-1
@SimpleFunction.vm$SIMPLEFUNCTION.TEST$LOOP_2
D;JGT
(SimpleFunction.vm$SIMPLEFUNCTION.TEST$END_2)

// PUSH LCL 0
@0
D=A
@LCL
AD=D+M
D=M
@SP
A=M
M=D
@SP
M=M+1

// PUSH LCL 1
@1
D=A
@LCL
AD=D+M
D=M
@SP
A=M
M=D
@SP
M=M+1

// add
@SP
A=M-1
D=M
@SP
M=M-1
@SP
A=M-1
M=D+M

// not
@SP
A=M-1
M=!M
// PUSH ARG 0
@0
D=A
@ARG
AD=D+M
D=M
@SP
A=M
M=D
@SP
M=M+1

// add
@SP
A=M-1
D=M
@SP
M=M-1
@SP
A=M-1
M=D+M

// PUSH ARG 1
@1
D=A
@ARG
AD=D+M
D=M
@SP
A=M
M=D
@SP
M=M+1

//SUB
@SP
A=M-1
D=M
@SP
M=M-1
@SP
A=M-1
M=M-D

//RETURN
// FRAME = LCL
@LCL
D=M
@R13
M=D

// RET = *(FRAME - 5) （get return address）
@5
A=D-A
D=M
@R14
M=D

// *ARG = pop() （return value to ARG[0]）
@SP
AM=M-1
D=M
@ARG
A=M
M=D

// SP = ARG + 1
@ARG
D=M+1
@SP
M=D

// restore THAT = *(FRAME - 1)
@R13
AM=M-1
D=M
@THAT
M=D

// restore THIS = *(FRAME - 2)
@R13
AM=M-1
D=M
@THIS
M=D

// restore ARG = *(FRAME - 3)
@R13
AM=M-1
D=M
@ARG
M=D

// restore LCL = *(FRAME - 4)
@R13
AM=M-1
D=M
@LCL
M=D

// GOTO RET
@14
A=M
0;JMP

