// 从键盘读取一个数字字符，将其转换为数字并存储到 RAM[1]

(WAIT_FOR_INPUT) // 等待键盘输入
    @KBD         // 读取键盘输入
    D=M          // D = 键盘输入
    @WAIT_FOR_INPUT
    D;JEQ        // 如果键盘未输入（D=0），继续等待

// 将 ASCII 转换为数字并存储到 RAM[1]
    @48          // 加载 '0' 的 ASCII 值
    D=D-A        // D = 键盘输入 - '0' （转换为数字）
    @1           // 指向 RAM[1]
    M=D          // 将结果存储到 RAM[1]

// 无限循环，表示程序结束
(END)
    @END
    0;JMP
