
// Computes R1=1 + ... + R0
// i = 1
@16

M=1
// sum = 0
@17

M = 0
// if i>R0 goto STOP
@16
D=M
@0
D=D-M
@18
D;JGT
// sum += i
@16
D=M
@17
M=D+ M
// i++
@16
M=M+1
@4