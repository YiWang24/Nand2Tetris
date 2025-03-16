// Init
@256            
D=A
@SP
M=D            

@Sys.init       
0;JMP    

// FUNCTION test.vm.SYS.INIT 0
// Function's entry point
(test.vm.SYS.INIT)
//push nVars 0 values (initializes the callee’s local variables)
@0
D=A
(LOOP_1)
@END_1
D;JEQ
@0
D=A
@SP
A=M
M=D
@SP
M=M+1
D=D-1
@LOOP_1
D;JGT
(END_1)

// PUSH CONSTANT 4000
@4000
D=A
@SP
A=M
M=D
@SP
M=M+1

// POP POINT 3
@SP
AM=M-1
D=M
@3
M=D

// PUSH CONSTANT 5000
@5000
D=A
@SP
A=M
M=D
@SP
M=M+1

// POP POINT 4
@SP
AM=M-1
D=M
@4
M=D

// CALL test.vm.SYS.MAIN 0
// ---- Step 1: Push return address ----
@RETURN_ADDRESS_6
D=A
@SP
A=M
M=D
@SP
M=M+1
// ---- Step 2: Push LCL ----
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1
// ---- Step 3: Push ARG ----
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1
// ---- Step 4: Push THIS ----
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
// ---- Step 5: Push THAT ----
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
// ---- Step 6: ARG = SP - (n + 5) ----
@SP
D=M
@0
D=D-A
@5
D=D-A
@ARG
M=D
// ---- Step 7: LCL = SP ----
@SP
D=M
@LCL
M=D
// ---- Step 8: Goto function ----
@test.vm.SYS.MAIN
0;JMP
// ---- Step 9: Declare return address label ----
(RETURN_ADDRESS_6)

//POP TEMP 1
@1
D=A
@5
D=D+A
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D

// LABEL LOOP
(LOOP)

// GOTO LOOP
@LOOP
0;JMP

// FUNCTION test.vm.SYS.MAIN 5
// Function's entry point
(test.vm.SYS.MAIN)
//push nVars 0 values (initializes the callee’s local variables)
@5
D=A
(LOOP_10)
@END_10
D;JEQ
@0
D=A
@SP
A=M
M=D
@SP
M=M+1
D=D-1
@LOOP_10
D;JGT
(END_10)

// PUSH CONSTANT 4001
@4001
D=A
@SP
A=M
M=D
@SP
M=M+1

// POP POINT 3
@SP
AM=M-1
D=M
@3
M=D

// PUSH CONSTANT 5001
@5001
D=A
@SP
A=M
M=D
@SP
M=M+1

// POP POINT 4
@SP
AM=M-1
D=M
@4
M=D

// PUSH CONSTANT 200
@200
D=A
@SP
A=M
M=D
@SP
M=M+1

//POP LCL 1
@1
D=A
@LCL
D=D+M
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D

// PUSH CONSTANT 40
@40
D=A
@SP
A=M
M=D
@SP
M=M+1

//POP LCL 2
@2
D=A
@LCL
D=D+M
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D

// PUSH CONSTANT 6
@6
D=A
@SP
A=M
M=D
@SP
M=M+1

//POP LCL 3
@3
D=A
@LCL
D=D+M
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D

// PUSH CONSTANT 123
@123
D=A
@SP
A=M
M=D
@SP
M=M+1

// CALL test.vm.SYS.ADD12 1
// ---- Step 1: Push return address ----
@RETURN_ADDRESS_22
D=A
@SP
A=M
M=D
@SP
M=M+1
// ---- Step 2: Push LCL ----
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1
// ---- Step 3: Push ARG ----
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1
// ---- Step 4: Push THIS ----
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
// ---- Step 5: Push THAT ----
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
// ---- Step 6: ARG = SP - (n + 5) ----
@SP
D=M
@1
D=D-A
@5
D=D-A
@ARG
M=D
// ---- Step 7: LCL = SP ----
@SP
D=M
@LCL
M=D
// ---- Step 8: Goto function ----
@test.vm.SYS.ADD12
0;JMP
// ---- Step 9: Declare return address label ----
(RETURN_ADDRESS_22)

//POP TEMP 0
@0
D=A
@5
D=D+A
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D

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

// PUSH LCL 2
@2
D=A
@LCL
AD=D+M
D=M
@SP
A=M
M=D
@SP
M=M+1

// PUSH LCL 3
@3
D=A
@LCL
AD=D+M
D=M
@SP
A=M
M=D
@SP
M=M+1

// PUSH LCL 4
@4
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

// add
@SP
A=M-1
D=M
@SP
M=M-1
@SP
A=M-1
M=D+M

// add
@SP
A=M-1
D=M
@SP
M=M-1
@SP
A=M-1
M=D+M

// add
@SP
A=M-1
D=M
@SP
M=M-1
@SP
A=M-1
M=D+M

//RETURN
// FRAME = LCL
@LCL
D=M
@FRAME
M=D

// RET = *(FRAME - 5) （get return address）
@5
D=A
@FRAME
A=M-D
D=M
@RET
M=D

// *ARG = pop() （return value to ARG[0]）
@SP
A=M-1
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
@FRAME
A=M-1
D=M
@THAT
M=D

// restore THIS = *(FRAME - 2)
@FRAME
D=M
@2
A=D-A
D=M
@THIS
M=D

// restore ARG = *(FRAME - 3)
@FRAME
D=M
@3
A=D-A
D=M
@ARG
M=D

// restore LCL = *(FRAME - 4)
@FRAME
D=M
@4
A=D-A
D=M
@LCL
M=D

// GOTO RET
@RET
A=M
0;JMP

// FUNCTION test.vm.SYS.ADD12 0
// Function's entry point
(test.vm.SYS.ADD12)
//push nVars 0 values (initializes the callee’s local variables)
@0
D=A
(LOOP_34)
@END_34
D;JEQ
@0
D=A
@SP
A=M
M=D
@SP
M=M+1
D=D-1
@LOOP_34
D;JGT
(END_34)

// PUSH CONSTANT 4002
@4002
D=A
@SP
A=M
M=D
@SP
M=M+1

// POP POINT 3
@SP
AM=M-1
D=M
@3
M=D

// PUSH CONSTANT 5002
@5002
D=A
@SP
A=M
M=D
@SP
M=M+1

// POP POINT 4
@SP
AM=M-1
D=M
@4
M=D

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

// PUSH CONSTANT 12
@12
D=A
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

//RETURN
// FRAME = LCL
@LCL
D=M
@FRAME
M=D

// RET = *(FRAME - 5) （get return address）
@5
D=A
@FRAME
A=M-D
D=M
@RET
M=D

// *ARG = pop() （return value to ARG[0]）
@SP
A=M-1
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
@FRAME
A=M-1
D=M
@THAT
M=D

// restore THIS = *(FRAME - 2)
@FRAME
D=M
@2
A=D-A
D=M
@THIS
M=D

// restore ARG = *(FRAME - 3)
@FRAME
D=M
@3
A=D-A
D=M
@ARG
M=D

// restore LCL = *(FRAME - 4)
@FRAME
D=M
@4
A=D-A
D=M
@LCL
M=D

// GOTO RET
@RET
A=M
0;JMP

