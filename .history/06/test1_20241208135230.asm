// 初始化变量
@0
M=0         // RAM[0] = 0 (用于存储第一个输入结果)
@1
M=0         // RAM[1] = 0 (用于存储第二个输入结果)
@RAM_TMP
M=0         // RAM_TMP = 0 (用于辅助模拟乘法)

// 读取第一个数字输入并解析
(READ_RAM0)
    @KBD
    D=M         // 读取键盘输入
    @READ_RAM0
    D;JEQ       // 如果没有输入，继续等待

    @END_RAM0
    @48
    D=D-A       // 将 ASCII 转换为数字（无检查）
    @RAM_TMP
    M=0         // 清零 RAM_TMP 用于模拟乘法
