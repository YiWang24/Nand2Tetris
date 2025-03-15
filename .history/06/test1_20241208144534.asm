// 等待从 KBD 获取 RAM[0] 的值
@WAIT_FOR_KBD0
D=M          // D = KBD（读取键盘输入）
@WAIT_FOR_KBD0
D;JEQ        // 如果 KBD 的值为 0（未输入），则继续等待
@0
M=D          // 将 KBD 输入值存储到 RAM[0]

// 等待从 KBD 获取 RAM[1] 的值
@WAIT_FOR_KBD1
D=M          // D = KBD（读取键盘输入）
@WAIT_FOR_KBD1
D;JEQ        // 如果 KBD 的值为 0（未输入），则继续等待
@1
M=D          // 将 KBD 输入值存储到 RAM[1]

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

// KBD 的地址映射，模拟键盘输入
(KBD)
    .fill 0 // 默认为 0，等待键盘输入
