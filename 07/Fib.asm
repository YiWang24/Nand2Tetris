// bootstrap
@256            
D=A
@SP
M=D            

// CALL SYS.INIT 0
@SYS.INIT$ret.0
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
@SYS.INIT
0;JMP
// (return-address)
(SYS.INIT$ret.0)
(MAIN.FIBONACCI)

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

// PUSH CONSTANT 2
@2
D=A
@SP
A=M
M=D
@SP
M=M+1

// lt
@SP
M=M-1
A=M
D=M
A=A-1
D=M-D
@LT_5
D;JLT
@SP
A=M-1
M=0
@END_5
0;JMP
(LT_5)
@SP
A=M-1
M=-1
(END_5)

// IF-GOTO N_LT_2
@SP
AM=M-1
D=M
@N_LT_2
D;JNE

// GOTO N_GE_2
@N_GE_2
0;JMP

// LABEL N_LT_2
(N_LT_2)

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

// LABEL N_GE_2
(N_GE_2)

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

// PUSH CONSTANT 2
@2
D=A
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

// CALL MAIN.FIBONACCI 1
@MAIN.FIBONACCI$ret.15
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
@MAIN.FIBONACCI
0;JMP
// (return-address)
(MAIN.FIBONACCI$ret.15)
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

// PUSH CONSTANT 1
@1
D=A
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

// CALL MAIN.FIBONACCI 1
@MAIN.FIBONACCI$ret.19
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
@MAIN.FIBONACCI
0;JMP
// (return-address)
(MAIN.FIBONACCI$ret.19)
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

(SYS.INIT)

// PUSH CONSTANT 4
@4
D=A
@SP
A=M
M=D
@SP
M=M+1

// CALL MAIN.FIBONACCI 1
@MAIN.FIBONACCI$ret.24
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
@MAIN.FIBONACCI
0;JMP
// (return-address)
(MAIN.FIBONACCI$ret.24)
// LABEL END
(END)

// GOTO END
@END
0;JMP

