// 初始化变量
@0
M=0         // RAM[0] = 0 (用于存储第一个输入结果)
@1
M=0         // RAM[1] = 0 (用于存储第二个输入结果)

// 读取第一个多位数字输入并解析
(READ_RAM0)
    @KBD
    D=M         // 读取键盘输入
    @READ_RAM0
    D;JEQ       // 如果输入为 0（无输入），继续等待

    @END_RAM0
   @KBD
D=M         // 从键盘读取输入（ASCII 值）

@48
D=D-A       // 将 ASCII 值减去 48，转换为数字

    @INVALID_INPUT
    D;JLT       // 如果小于 0，跳转到无效输入处理
    @9
    D=D-A
    @INVALID_INPUT
    D;JGT       // 如果大于 9，跳转到无效输入处理

    // 累加当前数字
    @0
    D=M         // 获取当前 RAM[0] 的值
    @10
    D=D*A       // 结果乘以 10
    @KBD
    D=D+M-48    // 加上当前输入的数字
    @0
    M=D         // 存回 RAM[0]
    @READ_RAM0  // 等待下一个输入
    0;JMP

(END_RAM0)
    @KBD
    D=M         // 再次读取键盘值，等待 Enter 键 (ASCII 13)
    @13
    D=D-A
    @END_RAM0
    D;JNE       // 如果不是 Enter，继续等待
    @READ_RAM1  // 转到读取第二个值
    0;JMP

// 读取第二个多位数字输入并解析
(READ_RAM1)
    @KBD
    D=M         // 读取键盘输入
    @READ_RAM1
    D;JEQ       // 如果输入为 0（无输入），继续等待

    @END_RAM1
    D=D-48      // 将 ASCII 转换为数字
    @INVALID_INPUT
    D;JLT       // 如果小于 0，跳转到无效输入处理
    @9
    D=D-A
    @INVALID_INPUT
    D;JGT       // 如果大于 9，跳转到无效输入处理

    // 累加当前数字
    @1
    D=M         // 获取当前 RAM[1] 的值
    @10
    D=D*A       // 结果乘以 10
    @KBD
    D=D+M-48    // 加上当前输入的数字
    @1
    M=D         // 存回 RAM[1]
    @READ_RAM1  // 等待下一个输入
    0;JMP

(END_RAM1)
    @KBD
    D=M         // 再次读取键盘值，等待 Enter 键 (ASCII 13)
    @13
    D=D-A
    @END_RAM1
    D;JNE       // 如果不是 Enter，继续等待

    @START_LOOP // 转到绘制像素的主循环
    0;JMP

// 无效输入处理
(INVALID_INPUT)
    @INVALID_INPUT
    0;JMP       // 无限循环，表示输入无效


// SCREEN 基地址
@16384
D=A
@SCREEN
M=D          // SCREEN = 16384

// 获取 RAM[0] 对应的屏幕地址
@0
D=M          // D = RAM[0] (起始像素位置)
@SCREEN
D=D+M        // D = SCREEN + RAM[0]
@CUR_PIXEL
M=D          // 保存当前像素地址到 CUR_PIXEL

// 开始循环
(LOOP)
    @1
    D=M      // D = RAM[1]
    @END
    D;JEQ    // 如果 RAM[1] == 0，退出循环

    // 设置当前像素为黑色
    @CUR_PIXEL
    A=M      // A = CUR_PIXEL
    M=-1     // 将当前像素设置为黑色 (-1)

    // 移动到下一个像素
    @CUR_PIXEL
    M=M+1    // CUR_PIXEL++

    // RAM[1]--
    @1
    M=M-1    // RAM[1] = RAM[1] - 1

    // 跳回 LOOP
    @LOOP
    0;JMP    // 回到循环开始

// 循环结束
(END)
    @END
    0;JMP    // 无限停止
