







// 初始化 RAM[0] = 3
@3
D=A
@0
M=D          // RAM[0] = 3

// 初始化 RAM[1] = 20
@20
D=A
@1
M=D          // RAM[1] = 20

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
