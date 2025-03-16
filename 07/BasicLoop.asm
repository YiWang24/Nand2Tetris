// bootstrap
@256            
D=A
@SP
M=D            


// PUSH CONSTANT 0
@0
D=A
@SP
A=M
M=D
@SP
M=M+1

//POP LCL 0
@0
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

// LABEL BasicLoop.vm$LOOP
(BasicLoop.vm$LOOP)

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

// add
@SP
A=M-1
D=M
@SP
M=M-1
@SP
A=M-1
M=D+M

//POP LCL 0
@0
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

//POP ARG 0
@0
D=A
@ARG
D=D+M
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
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

// IF-GOTO BasicLoop.vm$LOOP
@SP
AM=M-1
D=M
@BasicLoop.vm$LOOP
D;JNE

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

