// 从键盘读取最多两位数字，按 Enter 键结束，将结果存储到 RAM[1]

// 初始化 RAM[1] 为 0，用于存储最终结果
@1
M=0          // RAM[1] = 0

// 主循环：读取键盘输入
(READ_INPUT)
    @KBD
    D=M       // 读取键盘输入
    @READ_INPUT
    D;JEQ     // 如果没有输入，继续等待

    // 检查是否是 Enter 键 (ASCII 13)
    @13
    D=D-A
    @PROCESS_INPUT
    D;JNE     // 如果不是 Enter 键，处理数字输入

    // 如果是 Enter 键，跳转到结束逻辑
    @END_INPUT
    0;JMP

(PROCESS_INPUT)
    // 处理数字输入
    @KBD
    D=M       // 再次读取键盘输入
    @48
    D=D-A     // 转换为数字 (ASCII - '0')
    @temp
    M=D       // 将当前输入的数字存入 temp

    // 模拟 result = result * 10 + temp
    @1
    D=M       // D = 当前结果
    @10
    D=D+A     // D = 当前结果 * 10
    @temp
    D=D+M     // D = 当前结果 + 当前输入的数字
    @1
    M=D       // 更新结果到 RAM[1]

    // 等待按键抬起
(WAIT_KEYUP)
    @KBD
    D=M
    @WAIT_KEYUP
    D;JNE     // 如果按键未抬起，继续等待

    // 返回主循环继续读取下一个输入
    @READ_INPUT
    0;JMP

// 输入结束，程序结束
(END_INPUT)
    // 无限循环
(END)
    @END
    0;JMP

// 定义临时变量
@temp
M=0
