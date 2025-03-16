(SYS.INIT)

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

// CALL SYS.MAIN 0
@SYS.MAIN$ret.5
D=A
@SP
A=M
M=D
@SP
M=M+1
// push LCL
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1
// push ARG
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1
// push THIS
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
// push THAT
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
// ARG = SP-n-5
@SP
D=M
@0
D=D-A
@5
D=D-A
@ARG
M=D
// LCL = SP
@SP
D=M
@LCL
M=D
// goto f
@SYS.MAIN
0;JMP
// (return-address)
(SYS.MAIN$ret.5)
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

(SYS.MAIN)
@SP
A=M
M=0
@SP
M=M+1
@SP
A=M
M=0
@SP
M=M+1
@SP
A=M
M=0
@SP
M=M+1
@SP
A=M
M=0
@SP
M=M+1
@SP
A=M
M=0
@SP
M=M+1

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

// CALL SYS.ADD12 1
@SYS.ADD12$ret.21
D=A
@SP
A=M
M=D
@SP
M=M+1
// push LCL
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1
// push ARG
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1
// push THIS
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
// push THAT
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
// ARG = SP-n-5
@SP
D=M
@1
D=D-A
@5
D=D-A
@ARG
M=D
// LCL = SP
@SP
D=M
@LCL
M=D
// goto f
@SYS.ADD12
0;JMP
// (return-address)
(SYS.ADD12$ret.21)
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
@LCL
D=M
@frame
M=D // FRAME = LCL
@5
D=D-A
A=D
D=M
@return_address
M=D // RET = *(FRAME-5)
@SP
M=M-1
A=M
D=M
@ARG
A=M
M=D // *ARG = pop()
@ARG
D=M+1
@SP
M=D // SP = ARG+1
@frame
D=M-1
A=D
D=M
@THAT
M=D // THAT = *(FRAME-1)
@2
D=A
@frame
D=M-D
A=D
D=M
@THIS
M=D // THIS = *(FRAME-2)
@3
D=A
@frame
D=M-D
A=D
D=M
@ARG
M=D // ARG = *(FRAME-3)
@4
D=A
@frame
D=M-D
A=D
D=M
@LCL
M=D // LCL = *(FRAME-4)
@return_address
A=M
0;JMP // goto RET

(SYS.ADD12)

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
@LCL
D=M
@frame
M=D // FRAME = LCL
@5
D=D-A
A=D
D=M
@return_address
M=D // RET = *(FRAME-5)
@SP
M=M-1
A=M
D=M
@ARG
A=M
M=D // *ARG = pop()
@ARG
D=M+1
@SP
M=D // SP = ARG+1
@frame
D=M-1
A=D
D=M
@THAT
M=D // THAT = *(FRAME-1)
@2
D=A
@frame
D=M-D
A=D
D=M
@THIS
M=D // THIS = *(FRAME-2)
@3
D=A
@frame
D=M-D
A=D
D=M
@ARG
M=D // ARG = *(FRAME-3)
@4
D=A
@frame
D=M-D
A=D
D=M
@LCL
M=D // LCL = *(FRAME-4)
@return_address
A=M
0;JMP // goto RET

