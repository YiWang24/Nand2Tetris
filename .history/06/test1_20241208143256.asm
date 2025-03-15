// 初始化
@1
M=0   // RAM[1] 清零

// 第一步：读取第一个数字（十位）
(WAIT_FOR_INPUT_1)
@KBD
D=M
@WAIT_FOR_INPUT_1
D;JEQ    // 等待第一个数字输入

// 转换第一个数字
@48
D=D-A    // D = 第一个数字的数值

// 模拟乘以10的循环
@counter
M=D      // 将第一个数字存入 counter
@result
M=0      // result 清零

(MULTIPLY_BY_10)
@counter
D=M
@END_MULTIPLY
D;JEQ    // 如果 counter 为 0，结束乘法

@result
D=M
@10
D=D+A    // result += 10
@result
M=D

@counter
M=M-1    // counter--
@MULTIPLY_BY_10
0;JMP    // 继续循环

(END_MULTIPLY)
@result
D=M
@1
M=D      // 将乘法结果存入 RAM[1]

// 第二步：读取第二个数字（个位）
(WAIT_FOR_INPUT_2)
@KBD
D=M
@WAIT_FOR_INPUT_2
D;JEQ    // 等待第二个数字输入

// 转换第二个数字
@48
D=D-A    // D = 第二个数字的数值

// 将第二个数字加到结果上
@1
M=D    // RAM[1] += 第二个数字

// 结束程序
(END)
@END
0;JMP

// 辅助变量
@counter
M=0
@result
M=0