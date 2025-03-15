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
(LOOP_MULT_RAM0)
    @RAM_TMP
    D=M
    @10
    M=D+M       // 模拟乘法：结果 *= 10
    @RAM_TMP
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

    @END_RAM1
    @48
    D=D-A       // 将 ASCII 转换为数字（无检查）
    @RAM_TMP
    M=0         // 清零 RAM_TMP 用于模拟乘法
(LOOP_MULT_RAM1)
    @RAM_TMP
    D=M
    @10
    M=D+M       // 模拟乘法：结果 *= 10
    @RAM_TMP
    M=D
    @1
    D=M         // 获取当前结果 RAM[1]
    D=D+A       // 累加当前输入的数字
    @1
    M=D         // 保存到 RAM[1]

    @KBD
    D=M         // 检查是否按下 Enter 键
    @13         // ASCII 13 是 Enter 键
    D=D-A
    @END_RAM1
    D;JEQ       // 如果按下 Enter 键，结束第二个输入

    @READ_RAM1
    0;JMP       // 否则继续等待下一个输入

(END_RAM1)
    @START_LOOP
    0;JMP       // 转到主绘制循环

// 绘制像素到屏幕
@16384
D=A
@SCREEN
M=D          // SCREEN = 16384

@0
D=M          // RAM[0]：起始像素位置
@SCREEN
D=D+M        // 计算初始屏幕地址
@CUR_PIXEL
M=D          // 保存到 CUR_PIXEL

(LOOP)
    @1
    D=M      // 获取当前的 RAM[1]
    @END
    D;JEQ    // 如果 RAM[1] == 0，退出循环

    // 绘制像素
    @CUR_PIXEL
    A=M
    M=-1     // 将像素设置为黑色 (-1)

    // 更新 CUR_PIXEL 指针
    @CUR_PIXEL
    M=M+1

    // 减少计数器
    @1
    M=M-1

    @LOOP
    0;JMP

(END)
    @END
    0;JMP     // 无限停止
