// 键盘多位数输入程序
// 功能：从键盘读取多位数字，按Enter结束输入，将结果存入RAM[0]

// 定义寄存器和内存地址
@result    // 用于存储当前计算的数字
M=0

@digit     // 用于存储当前输入的数字
M=0

(MAIN_LOOP)
    // 检查键盘输入
    @KBD     
    D=M

    // 如果没有输入，继续等待
    @MAIN_LOOP
    D;JEQ

    @KBD
    D=M
    @48
    D=D-A

    // 将当前数字存入digit
    @digit
    M=D

    // 实现乘法：result = result * 10 + digit
    // 首先计算 result * 9
    @result
    D=M
    @temp
    M=D   // temp = result

    @8
    D=A
    @result
    M=0   // result清零

(MULTIPLY_LOOP)
    @temp
    D=M
    @result
    M=D+M  // result += temp
    @8
    MD=D-1 // 循环8次相当于乘9
    @MULTIPLY_LOOP
    D;JGT

    // 最后加上result本身（相当于 * 10）
    @temp
    D=M
    @result
    M=D+M

    // 加上当前digit
    @digit
    D=M
    @result
    M=D+M

    // 等待按键抬起
    (WAIT_KEYUP)
    @KBD
    D=M
    @WAIT_KEYUP
    D;JNE

    @MAIN_LOOP

(NOT_DIGIT)
    // 检查是否是Enter键（ASCII码为13）
    @KBD
    D=M
    @13
    D=D-A
    @MAIN_LOOP
    D;JNE

    // Enter键按下，将result存入RAM[0]
    @result
    D=M
    @0
    M=D

    // 程序结束，进入无限循环
    (END)
    @END
    0;JMP