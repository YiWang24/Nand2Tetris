// 初始化 RAM[0] 为 0，表示没有按下任何键
@0
M=0          // RAM[0] = 0

// 主循环：不断检查键盘输入
(INFINITE_LOOP)
// 检查键盘输入是否为 'A'（KBD == 65）
@24576       // KBD 地址
D=M          // D = KBD 输入值
@65
D=D-A        // D = D - 65，判断是否为 'A' 键
@CHECK_RELEASE
D;JEQ        // 如果 D == 0（键盘输入为 'A'），跳转到 CHECK_RELEASE

// 如果不是 'A' 键，继续循环
@INFINITE_LOOP
0;JMP

// 处理 'A' 键按下的逻辑
(CHECK_RELEASE)
// 等待按键释放（KBD == 0）
@WAIT_FOR_RELEASE
D=M          // D = KBD
@WAIT_FOR_RELEASE
D;JNE        // 如果 D != 0，继续等待，直到 'A' 键释放

// 防抖机制：确保按键被释放后等待 100 个 CPU 周期
@100
D=A          // D = 100（防抖等待时间）
(DEBOUNCE_LOOP)
    @DEBOUNCE_DONE
    D=D-1    // D 递减
    @DEBOUNCE_LOOP
    D;JGT    // 如果 D > 0，继续循环

// 按键释放后，增加按键计数（RAM[0] 增加 1）
(DEBOUNCE_DONE)
@0
M=M+1        // RAM[0] = RAM[0] + 1

// 返回主循环，继续检测按键
@INFINITE_LOOP
0;JMP
