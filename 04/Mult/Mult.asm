// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/4/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)
// The algorithm is based on repetitive addition.

// for(i=R0;i>0;i--) 
// R2 = R2 + R1


// i = R0
@R0
D=M
@i
M=D
// R2=0
@R2
M=0

// if(i=0) goto CONT
(LOOP)
@i
D=M
@CONT
D;JEQ

//R2 = R2 + R1
@R1
D=M
@R2
M=D+M

//i=i-1
@i
M=M-1
@LOOP
0;JMP

// End of the loop
(CONT)
@END
0;JMP

(END)
0;JMP

