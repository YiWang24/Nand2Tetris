// 初始化变量
@0
M=0         // RAM[0] = 0 (用于存储第一个输入结果)
@1
M=0         // RAM[1] = 0 (用于存储第二个输入结果)
@2
M=0         // 2 = 0 (用于辅助模拟乘法)

// 读取第一个数字输入并解析
(READ_RAM0)
    @KBD
    D=M         // 读取键盘输入
    @READ_RAM0
    D;JEQ       // 如果没有输入，继续等待

    @END_RAM0
    @48
    D=D-A       // 将 ASCII 转换为数字（无检查）
    @2
    M=0         // 清零 2 用于模拟乘法
(LOOP_MULT_RAM0)
    @2
    D=M
    @10
    M=D+M       // 模拟乘法：结果 *= 10
    @2
    M=D
    @0
    D=M         // 获取当前结果 RAM[0]
    D=D+A       // 累加当前输入的数字
    @0
    M=D         // 保存到 RAM[0]

    @KBD
    D=M         // 检查是否按下 Enter 键
    @13         // ASCII 13 是 Enter 键
    D=D-A
    @END_RAM0
    D;JEQ       // 如果按下 Enter 键，结束第一个输入

    @READ_RAM0
    0;JMP       // 否则继续等待下一个输入

(END_RAM0)
    @READ_RAM1
    0;JMP       // 转到读取第二个输入

// 读取第二个数字输入并解析
(READ_RAM1)
    @KBD
    D=M         // 读取键盘输入
    @READ_RAM1
    D;JEQ       // 如果没有输入，继续等待