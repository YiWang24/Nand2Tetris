// 初始化
@0
M=0   // RAM[0] 清零
@digit
M=0   // 当前数字清零

(START)
// 等待输入
(WAIT_INPUT)
@KBD
D=M
@WAIT_INPUT
D;JEQ   // 等待键盘输入

// 检查是否是回车键（ASCII 13）
@13
D=D-A
@CALCULATE_RESULT
D;JEQ   // 如果是回车，计算结果

// 恢复原始输入值
@KBD
D=M

// 转换字符为数字
@48
D=D-A   // D = 输入数字的实际值

// 模拟乘10
@result
M=0     // result 清零
@counter
M=D     // counter = 新输入的数字

(MULTIPLY_BY_10)
@counter
D=M
@ADD_NEW_DIGIT
D;JEQ   // 如果 counter 为 0，跳转到加新数字

@result
D=M
@10
D=D+A   // result += 10
@result
M=D

@counter
M=M-1   // counter--
@MULTIPLY_BY_10
0;JMP

// 加新数字
(ADD_NEW_DIGIT)
@result
D=M     // 取乘法结果
@digit
M=M+D   // 当前数字 = 原数字 + 新输入数字的乘10

@WAIT_INPUT  // 继续等待下一个输入
0;JMP

// 计算并存储最终结果
(CALCULATE_RESULT)
@digit
D=M
@0
M=D     // 将计算结果存入 RAM[0]

// 程序结束
(END)
@END
0;JMP

// 辅助变量
@counter
M=0
@result
M=0