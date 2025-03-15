// 初始化变量
@result
M=0         // result = 0
@digit
M=0         // digit = 0
@temp
M=0         // temp = 0

(MAIN_LOOP)
    @KBD
    D=M         // 读取键盘输入
    @MAIN_LOOP
    D;JEQ       // 如果没有输入，继续等待

    @KBD
    D=M        // 读取键盘输入
    @13
    D=D-A      // D = 键盘输入值 - 13
    @SAVE_RESULT
    D;JEQ      // 如果相等，跳转到保存结果


       @48         // 加载 '0' 的 ASCII 值到 A
    D=A         // D = 48 ('0' 的 ASCII 值)
    @KBD        // 读取键盘输入到 M
    D=M-D       // D = 键盘输入 - 48

    @NOT_DIGIT
    D;JLT       // 如果小于 0，跳转到 NOT_DIGIT
    @10
    D=D-A
    @NOT_DIGIT
    D;JGE       // 如果大于 9，跳转到 NOT_DIGIT

    @digit
    M=D         // 保存当前数字到 digit

    // 计算 result = result * 10
    @result
    D=M
    @temp
    M=D         // temp = result
    @9
    D=A
(MULTIPLY_LOOP)
    @temp
    D=M
    @result
    M=D+M       // result += temp
    D=D-1
    @MULTIPLY_LOOP
    D;JGT       // 循环 9 次

    @temp
    D=M
    @result
    M=D+M       // 加上 result 本身（*10）

    // 加上当前输入的数字 digit
    @digit
    D=M
    @result
    M=D+M

(WAIT_KEYUP)
    @KBD
    D=M
    @WAIT_KEYUP
    D;JNE       // 等待按键抬起

    @MAIN_LOOP
    0;JMP       // 返回主循环

(SAVE_RESULT)
    @result
    D=M
    @0
    M=D         // 保存结果到 RAM[0]

(END)
    @END
    0;JMP       // 无限循环
